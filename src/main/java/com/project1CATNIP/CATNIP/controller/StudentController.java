package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.*;
import com.project1CATNIP.CATNIP.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handles all interactions of students.
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository studentRepository;

    private final CohortRepository cohortRepository;

    private final CourseRepository courseRepository;

    @GetMapping({"", "/", "/all"})
    private String showAllStudents(Model model) {
        model.addAttribute("allStudents", studentRepository.findAll());

        return "/student/studentOverview";
    }

    @GetMapping("/add")
    private String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("allCohorts", cohortRepository.findAll());

        return "/student/studentAddForm";
    }

    @PostMapping("/add")
    private String saveStudent(@ModelAttribute("student") Student studentToAdd, BindingResult result) {

        if (!result.hasErrors()) {
            studentRepository.save(studentToAdd);
        }

        return "redirect:/student/add";
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
            return "/student/studentAddForm";
        }

        return "redirect:/student/all";
    }


}
