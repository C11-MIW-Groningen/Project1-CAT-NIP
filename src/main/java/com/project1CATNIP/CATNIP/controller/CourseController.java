package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.Course;
import com.project1CATNIP.CATNIP.model.Teacher;
import com.project1CATNIP.CATNIP.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>
 *
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseRepository courseRepository;

    @GetMapping({"/", "", "/all"})
    private String showAllCourses(Model model) {
        model.addAttribute("allCourses", courseRepository.findAll());

        return "courseOverview";
    }

    @GetMapping("/add")
    private String addCourseForm(Model model) {
        model.addAttribute("course", new Course());

        return "courseAddForm";
    }

    @PostMapping("/add")
    private String saveCourse(@ModelAttribute("course") Course courseToAdd, BindingResult result) {

        if (!result.hasErrors()) {
            courseRepository.save(courseToAdd);
        }

        return "redirect:/course/all";
    }

    @GetMapping("/delete/{courseId}")
    private String deleteCourse(@PathVariable("courseId") Long courseId) {
        Optional<Course> courseToDelete = courseRepository.findById(courseId);

        if (courseToDelete.isPresent()) {
            courseRepository.delete(courseToDelete.get());
        }

        return "redirect:/course/all";
    }
    //TODO Delete validatie

    @GetMapping("/edit/{courseId}")
    private String showEditCourseForm(@PathVariable("courseId") Long courseId, Model model) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isPresent()) {
            model.addAttribute("course", optionalCourse.get());
            return "courseAddForm";
        }

        return "redirect:/course/all";
    }
}
