package com.project1CATNIP.CATNIP.controller;/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.Course;
import com.project1CATNIP.CATNIP.model.Teacher;
import com.project1CATNIP.CATNIP.repository.CourseRepository;
import com.project1CATNIP.CATNIP.repository.ProgramRepository;
import com.project1CATNIP.CATNIP.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("course")
public class CourseController {

    private final CourseRepository courseRepository;
    private final ProgramRepository programRepository;
    private final TeacherRepository teacherRepository;

    private final String redirectOverview = "redirect:/course/all";
    //Todo: bij alle controllers een redirectOverview maken

    @GetMapping({"", "/","/all"})
    private String showAllCourses(Model model) {
        model.addAttribute("allCourses", courseRepository.findAll());

        return "courseOverview";
    }

    @GetMapping("/add")
    private String addCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("allPrograms", programRepository.findAll());
        model.addAttribute("allTeachers", teacherRepository.findAll());

        return "courseAddForm";
    }

    @PostMapping("/add")
    private String saveCourse(@ModelAttribute("course") Course course, BindingResult result) {
        if(!result.hasErrors()) {
            courseRepository.save(course);
        }

        return redirectOverview;
    }

    @GetMapping("/delete/{courseId}")
    private String deleteCourse(@PathVariable("courseId") Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            courseRepository.delete(optionalCourse.get());
        }

        return redirectOverview;
    }

    @GetMapping("/edit/{courseId}")
    private String showEditForm(@PathVariable("courseId") Long courseId, Model model) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isPresent()) {
            model.addAttribute("course", optionalCourse.get());
            model.addAttribute("allPrograms", programRepository.findAll());
            model.addAttribute("allTeachers", teacherRepository.findAll());
            return "courseAddForm";
        }

        return redirectOverview;
    }

    // Delete
    // ShowEditForm

}
