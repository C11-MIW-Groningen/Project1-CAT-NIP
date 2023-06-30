package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.*;
import com.project1CATNIP.CATNIP.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.Optional;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handles all interactions of students.
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final String ROLE_STUDENT = "ROLE_STUDENT";

    private final StudentRepository studentRepository;

    private final CohortRepository cohortRepository;

    private final MIWUserRepository miwUserRepository;

    @GetMapping({"", "/", "/all"})
    private String showAllStudents(Model model) {
        model.addAttribute("allStudents", studentRepository.findAll());

        return "/student/studentOverview";
    }

    @GetMapping("/add")
    private String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("allCohorts", cohortRepository.findAll());
        model.addAttribute("purpose", "Add a student");

        return "/student/studentAddForm";
    }

    @PostMapping("/add")
    private String saveStudent(@ModelAttribute("student") Student studentToAdd, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "/student/studentAddForm";
        }

        studentRepository.save(studentToAdd);
        String successMessage = "Student added successfully.";
        model.addAttribute("success", successMessage);
        return "/student/studentAddForm";
    }

    @GetMapping("/delete/{studentId}")
    private String deleteStudent(@PathVariable("studentId") Long studentId) {
        Optional<Student> studentToDelete = studentRepository.findById(studentId);

        if (studentToDelete.isPresent()) {
            studentRepository.delete(studentToDelete.get());
        }

        return "redirect:/student/all";
    }

    @GetMapping("/edit/{studentId}")
    private String showEditStudentForm(@PathVariable("studentId") Long studentId, Model model) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isPresent()) {
            model.addAttribute("student", optionalStudent.get());
            model.addAttribute("allCohorts", cohortRepository.findAll());
            model.addAttribute("purpose", "Edit a student");
            return "/student/studentAddForm";
        }

        return "redirect:/student/all";
    }

    @GetMapping("/mygrades")
    private String showGrades(Model model, Principal principal) throws AccessDeniedException {
        Optional<MIWUser> optionalUser = miwUserRepository.findByUsername(principal.getName());
        if (optionalUser.isEmpty()) {
            return "redirect:/";
        }

        MIWUser user = optionalUser.get();

        if (userIsRole(ROLE_STUDENT, user)) {
            model.addAttribute("grades", user.getStudent().getAllTestAttempts());
            model.addAttribute("student", user.getStudent());
            return "student/myGrades";
        } else {
            throw new AccessDeniedException("Access to grades denied.");
        }
    }

    @GetMapping("/mycourses")
    private String showCourses(Model model, Principal principal) throws AccessDeniedException {
        Optional<MIWUser> optionalUser = miwUserRepository.findByUsername(principal.getName());
        if (optionalUser.isEmpty()) {
            return "redirect:/";
        }

        MIWUser user = optionalUser.get();

        if (userIsRole(ROLE_STUDENT, user)) {
            model.addAttribute("courses", user.getStudent().getAllCourses());
            model.addAttribute("student", user.getStudent());
            return "student/myCourses";
        } else {
            throw new AccessDeniedException("Access to grades denied.");
        }
    }

    private boolean userIsRole(String role, MIWUser user) {
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}
