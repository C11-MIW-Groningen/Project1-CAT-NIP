package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.Test;
import com.project1CATNIP.CATNIP.model.TestItem;
import com.project1CATNIP.CATNIP.repository.CourseRepository;
import com.project1CATNIP.CATNIP.repository.TestItemRepository;
import com.project1CATNIP.CATNIP.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
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

    private final TestItemRepository testItemRepository;

    @GetMapping({"", "/", "/all"})
    private String showAllTests(Model model) {
        model.addAttribute("allTests", testRepository.findAll());

        return "/archive/overview";
    }

    @GetMapping("/add")
    private String addTestForm(Model model) {
        Test test = new Test();
        model.addAttribute("test", test);
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

    @GetMapping("/{testId}/addtestitems")
    private String addTestItemsForm(@PathVariable("testId") Long testId, Model model) {
        Optional<Test> optionalTest = testRepository.findById(testId);

        if (optionalTest.isPresent()) {
            model.addAttribute("test", optionalTest.get());
            model.addAttribute("testitem", new TestItem());
            List<TestItem> allTestItemsFromTest = optionalTest.get().getTestItems();
            model.addAttribute("allTestItemsFromTest", allTestItemsFromTest);
            return "/test/addTestItemsForm";
        }

        return "redirect:/course/all";
    }

    @PostMapping("/addtestitem")
    private String saveTestItem(@ModelAttribute("testitem") TestItem testItem,
                                BindingResult result) {
        if (!result.hasErrors()) {
            testItemRepository.save(testItem);
            return "redirect:/test/" + testItem.getTest().getTestId() + "/addtestitems";
        }

        return "redirect:/course/all";
    }

    @GetMapping("/details/{testId}")
    private String showTestDetails(@PathVariable("testId") Long testId, Model model) {
        Optional<Test> optionalTest = testRepository.findById(testId);

        if (optionalTest.isPresent()) {
            Test test = optionalTest.get();
            List<TestItem> testItemsFromTest = test.getTestItems();
            Collections.sort(testItemsFromTest);
            model.addAttribute("test", test);
            model.addAttribute("testItemsForTest", testItemsFromTest);
            return "/test/testDetails";
        }

        return "redirect:/course/all";
    }
}
