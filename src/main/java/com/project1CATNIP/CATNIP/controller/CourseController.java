package com.project1CATNIP.CATNIP.controller;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handles all interactions of courses.
 */

import com.project1CATNIP.CATNIP.model.Assignment;
import com.project1CATNIP.CATNIP.model.Course;
import com.project1CATNIP.CATNIP.model.Test;
import com.project1CATNIP.CATNIP.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseRepository courseRepository;

    private final ProgramRepository programRepository;

    private final TeacherRepository teacherRepository;

    private final AssignmentRepository assignmentRepository;

    private final TestRepository testRepository;

    @GetMapping({"", "/","/all"})
    private String showAllCourses(Model model) {
        model.addAttribute("allCourses", courseRepository.findAll());

        return "/course/courseOverview";
    }

    @GetMapping("/add")
    private String addCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("allPrograms", programRepository.findAll());
        model.addAttribute("allTeachers", teacherRepository.findAll());

        return "/course/courseAddForm";
    }

    @PostMapping("/add")
    private String saveCourse(@ModelAttribute("course") Course course, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "/course/courseAddForm";
        }

        courseRepository.save(course);
        String successMessage = "Course added successfully.";
        model.addAttribute("success", successMessage);

        return "redirect:/course/add";
    }

    @GetMapping("/delete/{courseId}")
    private String deleteCourse(@PathVariable("courseId") Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isPresent()) {
            for (Test test : testRepository.findByCourse(optionalCourse.get())) {
                test.setCourse(null);
                testRepository.save(test);
            }

            for (Assignment assignment : assignmentRepository.findByCourse(optionalCourse.get())) {
                assignment.setCourse(null);
                assignmentRepository.save(assignment);
            }

            courseRepository.delete(optionalCourse.get());
        }

        return "redirect:/course/all";
    }

    @GetMapping("/edit/{courseId}")
    private String showEditForm(@PathVariable("courseId") Long courseId, Model model) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isPresent()) {
            model.addAttribute("course", optionalCourse.get());
            model.addAttribute("allPrograms", programRepository.findAll());
            model.addAttribute("allTeachers", teacherRepository.findAll());
            return "/course/courseAddForm";
        }

        return "redirect:/course/all";
    }

    @GetMapping("/details/{courseId}")
    private String showCourseDetails(@PathVariable("courseId") Long courseId, Model  model) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            model.addAttribute("course", optionalCourse.get());
            model.addAttribute("testsFromCourse", testRepository.findByCourse(course));
            model.addAttribute("assignmentsFromCourse", assignmentRepository.findByCourse(course));
            return "/course/courseDetails";
        }

        return "redirect:/course/all";
    }
}
