package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.Teacher;
import com.project1CATNIP.CATNIP.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handles all teacher interactions.
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @GetMapping ({"", "/", "/all"})
    private String showAllTeachers(Model model) {
        model.addAttribute("allTeachers", teacherRepository.findAll());

        return "teacherOverview";
    }

    @GetMapping("/add")
    private String addTeacherForm(Model model) {
        model.addAttribute("teacher", new Teacher());

        return "teacherAddForm";
    }

    @PostMapping("/add")
    private String saveTeacher(@ModelAttribute("teacher") Teacher teacherToAdd, BindingResult result) {

        if (!result.hasErrors()) {
            teacherRepository.save(teacherToAdd);
        }

        return "redirect:/teacher/add";
        //TODO vinkje om keuze te geven om direct naar overview te gaan
    }

    @GetMapping("/delete/{teacherId}")
    private String deleteTeacher(@PathVariable("teacherId") Long teacherId) {
        Optional<Teacher> teacherToDelete = teacherRepository.findById(teacherId);

        if (teacherToDelete.isPresent()) {
            teacherRepository.delete(teacherToDelete.get());
        }

        return "redirect:/teacher/all";
    }
    //TODO Delete validatie

    @GetMapping("/edit/{teacherId}")
    private String showEditTeacherForm(@PathVariable("teacherId") Long teacherId, Model model) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);

        if (optionalTeacher.isPresent()) {
            model.addAttribute("teacher", optionalTeacher.get());
            return "teacherAddForm";
        }

        return "redirect:/teacher/all";
    }

    @GetMapping("/details/{teacherId}")
    private String showDetailsTeacher(@PathVariable("teacherId") Long teacherId, Model model) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);

        if (optionalTeacher.isPresent()) {
            model.addAttribute("teacher", optionalTeacher.get());
            return "teacherDetails";
        }

        return "redirect:/teacher/all";
    }
}
