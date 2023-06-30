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

        return "/testAttempt/overviewCohorts";
    }

    @GetMapping("/grading/{cohortId}")
    private String showCoursesForCohort(@PathVariable("cohortId") Long cohortId, Model model) {
        Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);

        if (optionalCohort.isEmpty()) {
            return "redirect:/grading/";
        }

        Cohort thisCohort = optionalCohort.get();
        List<Course> listCourses = courseRepository.findByProgram(thisCohort.getProgram());

        model.addAttribute("cohort", thisCohort);
        model.addAttribute("allCourses", listCourses);

        return "/testAttempt/overviewCourses";
    }

    @GetMapping("/grading/{cohortId}/{courseId}")
    private String showStudentsForCourse(
            @PathVariable("cohortId") Long cohortId, @PathVariable("courseId") Long courseId, Model model) {
        Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCohort.isEmpty() || optionalCourse.isEmpty()) {
            return "redirect:/grading/";
        }

        Cohort thisCohort = optionalCohort.get();

        List<Student> listStudents = studentRepository.findStudentsByCohort(thisCohort);
        model.addAttribute("allStudents", listStudents);
        model.addAttribute("course", optionalCourse.get());
        model.addAttribute("cohort", thisCohort);

        return "/testAttempt/overviewTestAttemptsPerCourse";
    }

    @GetMapping("/grading/add/{cohortId}")
    private String addTestAttempt(@PathVariable("cohortId") Long cohortId, Model model) {
        Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);

        if (optionalCohort.isEmpty()) {
            return "redirect:/grading";
        }

        Cohort cohort = optionalCohort.get();
        model.addAllAttributes(getPostAttributes(cohort));

        return "/testAttempt/testAttemptAddForm";
    }

    @PostMapping("/grading/add")
    private String saveTestAttempt(@ModelAttribute("newTestAttempt") TestAttempt testAttemptToSave,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/testAttempt/testAttemptAddForm";
        }

        if (testAttemptToSave.getTestAttemptId() != null) {
            testAttemptRepository.save(testAttemptToSave);
            return "redirect:/grading";
        }

        testAttemptRepository.save(testAttemptToSave);

        return "redirect:/grading/add/" + testAttemptToSave.getStudent().getCohort().getCohortId();
    }

    @GetMapping("/grading/edit/{testAttemptId}")
    private String showEditTestAttemptForm(@PathVariable("testAttemptId") Long testAttemptId, Model model) {
        Optional<TestAttempt> optionalTestAttempt = testAttemptRepository.findById(testAttemptId);

        if (optionalTestAttempt.isPresent()) {
            model.addAttribute("editTestAttempt", optionalTestAttempt.get());
            model.addAttribute("purpose", "Edit a test result");
            model.addAttribute("student", optionalTestAttempt.get().getStudent());
            model.addAttribute("test", optionalTestAttempt.get().getTest());
            return "/testAttempt/testAttemptEditForm";
        }

        return "redirect:/grading/all";
    }

    @PostMapping("/grading/edit")
    private String updateTestAttempt(@ModelAttribute("editTestAttempt") TestAttempt testAttemptToSave,
                                   BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/grading";
        }

        Long courseId = testAttemptToSave.getTest().getCourse().getCourseId();
        Long student = testAttemptToSave.getStudent().getStudentId();

        testAttemptRepository.save(testAttemptToSave);

        return "redirect:/grading/student/" + courseId + "/" + student;
    }

    @GetMapping("/grading/delete/{testAttemptId}")
    private String deleteTestAttempt(@PathVariable("testAttemptId") Long testAttemptId) {
        Optional<TestAttempt> optionalTestAttempt = testAttemptRepository.findById(testAttemptId);

        if (optionalTestAttempt.isPresent()) {
            testAttemptRepository.delete(optionalTestAttempt.get());
            return "redirect:/grading/student/" + optionalTestAttempt.get().getStudent().getStudentId();
        }

        return "redirect:/grading/";
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
        return "/testAttempt/overviewStudent";
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
        List<TestAttempt> testAttempts = testAttemptRepository.
                findTestAttemptsByStudentAndTestIn(student, optionalCourse.get().getTests());

        model.addAttribute("student", student);
        model.addAttribute("allTestAttempts", testAttempts);

        return "testAttempt/overviewStudentCourse";
    }

    private List<Course> getCoursesForStudent(Student student) {
        Cohort cohort = student.getCohort();
        Program program = cohort.getProgram();
        return courseRepository.findByProgram(program);
    }

    private Map<String, Object> getPostAttributes(Cohort cohort) {
        Map<String, Object> attributeMap = new HashMap<>();

        attributeMap.put("allStudents", studentRepository.findStudentsByCohort(cohort));
        attributeMap.put("cohort", cohort);
        attributeMap.put("allTests", testRepository.findAll());
        attributeMap.put("purpose", "Add a test result for: " + cohort.getDisplayCohort());
        attributeMap.put("newTestAttempt", new TestAttempt());

        return attributeMap;
    }
}
