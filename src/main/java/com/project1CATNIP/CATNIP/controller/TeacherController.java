package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.Teacher;
import com.project1CATNIP.CATNIP.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl, Marcel Tubben <mhg.tubben@st.hanze.nl>>
 * Doel van je programma.
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherRepository teacherRepository;

    @GetMapping ("/all")
    private String showAllTeachers(Model model) {
        model.addAttribute("allTeachers", teacherRepository.findAll());

        return "teacherOverview";
    }

    @GetMapping("/add")
    private String addTeacherForm(Model model) {
        model.addAttribute("newTeacher", new Teacher());

        return "teacherAddForm";
    }

    @PostMapping("/add")
    private String saveTeacher(@ModelAttribute("newTeacher") Teacher teacherToAdd, BindingResult result) {

        if (!result.hasErrors()) {
            teacherRepository.save(teacherToAdd);
        }

        return "redirect:/teacher/add";
        //TODO vinkje om keuze te geven om direct naar overview te gaan
    }
}
