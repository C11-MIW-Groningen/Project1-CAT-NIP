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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

        model.addAttribute("allTestAttemptsResults", getAllHighestTestAttempts(listStudents, course));
        model.addAttribute("thisCourse", course);
        model.addAttribute("thisCohort", cohort);

        return "/test_attempt/overviewTestAttemptsPerCourse";
    }

    //Todo: voor vrijdag 23-06 gebruiken of anders deleten!
//    @GetMapping("/grading/add/{cohortId}/{courseId}/")
//    private String showAddGradingForm(
//            @PathVariable("courseId") Long courseId,
//            @PathVariable("cohortId") Long cohortId,
//            Model model) {
//        Optional<Course> optionalCourse = courseRepository.findById(courseId);
//        Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);
//
//        if (optionalCourse.isEmpty() || optionalCohort.isEmpty()) {
//            return "redirect:/grading/";
//        }
//
//        Cohort cohort = optionalCohort.get();
//        Course course = optionalCourse.get();
//        List<Student> studentList = studentRepository.findStudentsByCohort(cohort);
//
//        model.addAttribute("studentsForCohort", studentList);
//        model.addAttribute("thisCourse", course);
//        model.addAttribute("testsForCourse", testRepository.findByCourse(course));
//
//        return "/test_attempt/selectAddForm";
//    }

    private TestAttempt getHighestTestAttempt(Student student, Course course) {
        List<TestAttempt> testAttempts = testAttemptRepository.
                findTestAttemptsByStudentAndTestIn(student, course.getTests());

        TestAttempt highestTestAttempt = null;
        double highestResult = 0;

        for (TestAttempt testAttempt : testAttempts) {
            if (testAttempt.getAttemptResult() > highestResult) {
                highestTestAttempt = testAttempt;
                highestResult = testAttempt.getAttemptResult();
            }
        }

        return highestTestAttempt;
    }

    private List<TestAttempt> getAllHighestTestAttempts(List<Student> studentList, Course course) {
        List<TestAttempt> allHighestTestAttempts = new ArrayList<>();
        for (Student student : studentList) {
            allHighestTestAttempts.add(getHighestTestAttempt(student, course));
        }

        return allHighestTestAttempts;
    }

}
