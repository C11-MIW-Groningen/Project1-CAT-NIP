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
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handles all interactions of students.
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;
    private final CohortRepository cohortRepository;
    private final CourseRepository courseRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final ProgramRepository programRepository;
    private final TestRepository testRepository;

    @GetMapping({"", "/", "/all"})
    private String showAllStudents(Model model) {
        model.addAttribute("allStudents", studentRepository.findAll());

        return "studentOverview";
    }

    @GetMapping("/add")
    private String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("allCohorts", cohortRepository.findAll());

        return "studentAddForm";
    }

    @PostMapping("/add")
    private String saveStudent(@ModelAttribute("student") Student studentToAdd, BindingResult result) {

        if (!result.hasErrors()) {
            studentRepository.save(studentToAdd);
        }

        return "redirect:/student/add";
        //TODO vinkje om keuze te geven om direct naar overview te gaan
    }

    @GetMapping("/delete/{studentId}")
    private String deleteStudent(@PathVariable("studentId") Long studentId) {
        Optional<Student> studentToDelete = studentRepository.findById(studentId);

        if (studentToDelete.isPresent()) {
            studentRepository.delete(studentToDelete.get());
        }

        return "redirect:/student/all";
    }
    //TODO Delete validatie

    @GetMapping("/edit/{studentId}")
    private String showEditStudentForm(@PathVariable("studentId") Long studentId, Model model) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isPresent()) {
            model.addAttribute("student", optionalStudent.get());
            return "studentAddForm";
        }

        return "redirect:/student/all";
    }

    @GetMapping("/details/{studentId}")
    private String showStudentDetails(@PathVariable("studentId") Long studentId, Model  model) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            model.addAttribute("thisStudent", student);
            model.addAttribute("courses", getCoursesForStudent(student));
            return "studentDetails";
        }

        return "redirect:/student/all";
    }

    private List<Course> getCoursesForStudent(Student student) {
        Cohort cohort = student.getCohort();
        Program program = cohort.getProgram();
        return courseRepository.findByProgram(program);
    }


}
