package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.Test;
import com.project1CATNIP.CATNIP.repository.CourseRepository;
import com.project1CATNIP.CATNIP.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handles all interactions of tests.
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestRepository testRepository;

    private final CourseRepository courseRepository;

    @GetMapping({"", "/", "/all"})
    private String showAllTests(Model model) {
        model.addAttribute("allTests", testRepository.findAll());

        return "/test/overview";
    }

    @GetMapping("/add")
    private String addTestForm(Model model) {
        model.addAttribute("test", new Test());
        model.addAttribute("allCourses", courseRepository.findAll());

        return "/test/addForm";
    }

    @PostMapping("/add")
    private String saveTest(@ModelAttribute("test") Test testToAdd, BindingResult result) {
        if (!result.hasErrors()) {
            testRepository.save(testToAdd);
        }

        return "redirect:/test/add";
        //TODO vinkje om keuze te geven om direct naar overview te gaan
    }

    @GetMapping("/delete/{testId}")
    private String deleteTest(@PathVariable("testId") Long testId) {
        Optional<Test> testToDelete = testRepository.findById(testId);

        if (testToDelete.isPresent()) {
            testRepository.delete(testToDelete.get());
        }

        return "redirect:/test/all";
    }
    //TODO Delete validatie

    @GetMapping("/edit/{testId}")
    private String showEditTestForm(@PathVariable("testId") Long testId, Model model) {
        Optional<Test> optionalTest = testRepository.findById(testId);

        if (optionalTest.isPresent()) {
            model.addAttribute("test", optionalTest.get());
            model.addAttribute("allCourses", courseRepository.findAll());
            return "/test/addForm";
        }

        return "redirect:/test/all";
    }
}
