package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>
 * Handles all interactions of tests.
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final TestRepository testRepository;

    @GetMapping({"", "/", "/all"})
    private String showAllTests(Model model) {
        model.addAttribute("allTests", testRepository.findAll());

        return "testOverview";
    }
}
