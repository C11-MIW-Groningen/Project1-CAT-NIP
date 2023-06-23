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
        List<TestAttempt> listTestAttempts = getHighestTestAttemptsForCourse(listStudents, course);
        model.addAttribute("allStudentsForCohort", listStudents);
        model.addAttribute("allTestAttemptsResults", listTestAttempts);
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

    //Haalt alle TestAttempts voor een vak van een student op en geeft de TestAttempt terug met het hoogste cijfer
    public TestAttempt getHighestTestAttemptForCourse(Student student, Course course) {
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

    // Haalt voor een vak alle studenten van een cohort op
    // en geeft voor elke student de TestAttempt met het hoogste cijfer terug
    private List<TestAttempt> getHighestTestAttemptsForCourse(List<Student> studentList, Course course) {
        List<TestAttempt> allHighestTestAttempts = new ArrayList<>();

        for (Student student : studentList) {
            if (!testAttemptRepository.findTestAttemptsByStudentAndTestIn(student, course.getTests()).isEmpty()) {
                allHighestTestAttempts.add(getHighestTestAttemptForCourse(student, course));
            }
        }

        return allHighestTestAttempts;
    }

}
