package com.project1CATNIP.CATNIP.controller;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.*;
import com.project1CATNIP.CATNIP.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class TestAttemptController {

    private final TestAttemptRepository testAttemptRepository;

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private final CohortRepository cohortRepository;

    private final TestRepository testRepository;

    @GetMapping("/grading")
    private String showCohorts(Model model) {
        model.addAttribute("allCohorts", cohortRepository.findAll());

        return "/test_attempt/overviewCohorts";
    }

    @GetMapping("/grading/{cohortId}")
    private String showCoursesForCohort(@PathVariable("cohortId") Long cohortId, Model model) {
        Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);

        if (optionalCohort.isEmpty()) {
            return "redirect:/grading/";
        }

        Cohort cohort = optionalCohort.get();
        List<Course> listCourses = courseRepository.findByProgram(cohort.getProgram());

        model.addAttribute("thisCohort", cohort);
        model.addAttribute("allCourses", listCourses);

        return "/test_attempt/overviewCourses";
    }

    @GetMapping("/grading/{cohortId}/{courseId}")
    private String showStudentsForCourse(
            @PathVariable("cohortId") Long cohortId, @PathVariable("courseId") Long courseId, Model model) {
        Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCohort.isEmpty() || optionalCourse.isEmpty()) {
            return "redirect:/grading/";
        }

        Cohort cohort = optionalCohort.get();
        Course course = optionalCourse.get();

        List<Student> listStudents = studentRepository.findStudentsByCohort(cohort);
        model.addAttribute("allStudentsForCohort", listStudents);
        model.addAttribute("thisCourse", course);
        model.addAttribute("thisCohort", cohort);

        return "/test_attempt/overviewTestAttemptsPerCourse";
    }

    @GetMapping("/grading/add/{cohortId}")
    private String addTestAttempt(@PathVariable("cohortId") Long cohortId, Model model) {
        Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);

        if (optionalCohort.isEmpty()) {
            return "redirect:/grading";
        }
        Cohort cohort = optionalCohort.get();

        model.addAttribute("cohort", cohort);
        model.addAttribute("allStudents", studentRepository.findStudentsByCohort(cohort));
        model.addAttribute("allTests", testRepository.findAll());
        model.addAttribute("newTestAttempt", new TestAttempt());

        return "/test_attempt/testAttemptAddForm";
    }

    @PostMapping("/grading/add")
    private String saveTestAttempt(@ModelAttribute("newTestAttempt") TestAttempt testAttemptToSave,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/testAttempt/testAttemptAddForm";
        }

        testAttemptRepository.save(testAttemptToSave);
        String successMessage = "Test result added successfully.";
        model.addAttribute("success", successMessage);
        return "redirect:/grading";
    }

    @GetMapping("/grading/student/{studentId}")
    private String showGradesForStudent(@PathVariable("studentId") Long studentId, Model model) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isEmpty()) {
            return "redirect:/student/all";
        }
        Student student = optionalStudent.get();
        List<Course> studentCourses = getCoursesForStudent(student);

        model.addAttribute("student", student);
        model.addAttribute("courses", studentCourses);
        return "/test_attempt/overviewStudent";
    }

    @GetMapping("/grading/student/{studentId}/{courseId}")
    private String showAllTestAttemptsForStudent(
            @PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId, Model model) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalStudent.isEmpty() || optionalCourse.isEmpty()) {
            return "redirect:/grading/";
        }

        Student student = optionalStudent.get();
        Course course = optionalCourse.get();
        List<TestAttempt> testAttempts = testAttemptRepository.
                findTestAttemptsByStudentAndTestIn(student, course.getTests());

        model.addAttribute("student", student);
        model.addAttribute("course", student);
        model.addAttribute("allTestAttempts", testAttempts);

        return "test_attempt/overviewStudentCourse";
    }

    private List<Course> getCoursesForStudent(Student student) {
        Cohort cohort = student.getCohort();
        Program program = cohort.getProgram();
        return courseRepository.findByProgram(program);
    }
}
