package com.project1CATNIP.CATNIP.controller;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.Cohort;
import com.project1CATNIP.CATNIP.model.Course;
import com.project1CATNIP.CATNIP.model.Student;
import com.project1CATNIP.CATNIP.model.TestAttempt;
import com.project1CATNIP.CATNIP.repository.CohortRepository;
import com.project1CATNIP.CATNIP.repository.CourseRepository;
import com.project1CATNIP.CATNIP.repository.StudentRepository;
import com.project1CATNIP.CATNIP.repository.TestAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TestAttemptController {

    private final TestAttemptRepository testAttemptRepository;

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private final CohortRepository cohortRepository;

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
        List<TestAttempt> listTestAttempts = testAttemptRepository
                .findTestAttemptsByStudentInAndTestIn(listStudents, course.getTests());

        model.addAttribute("allTestAttempts", listTestAttempts);
        model.addAttribute("thisCourse", course);

        return "/test_attempt/overviewTestAttemptsPerCourse";
    }

}
