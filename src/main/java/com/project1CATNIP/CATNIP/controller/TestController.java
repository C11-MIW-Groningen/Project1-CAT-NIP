package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.Test;
import com.project1CATNIP.CATNIP.model.TestItem;
import com.project1CATNIP.CATNIP.repository.CourseRepository;
import com.project1CATNIP.CATNIP.repository.TestItemRepository;
import com.project1CATNIP.CATNIP.repository.TestRepository;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/add")
    private String addTestForm(Model model) {
        model.addAttribute("test", new Test());
        model.addAttribute("allCourses", courseRepository.findAll());

        return "/test/addForm";
    }

    @PostMapping("/add")
    private String saveTest(@ModelAttribute("test") Test testToAdd, BindingResult result) {
        if (result.hasErrors()) {
            return "/test/addForm";
        }

        testRepository.save(testToAdd);

        String course = testToAdd.getCourse().getCourseId().toString();
        return "redirect:/course/details/" + course;
    }

    @GetMapping("/delete/{testId}")
    private String deleteTest(@PathVariable("testId") Long testId) {
        Optional<Test> testToDelete = testRepository.findById(testId);

        if (testToDelete.isPresent()) {
            Test test = testToDelete.get();
            List<TestItem> testItemList = testItemRepository.findTestItemsByTest(test);
            testItemRepository.deleteAll(testItemList);
            testRepository.delete(test);

            return "redirect:/course/details/" + testToDelete.get().getCourse().getCourseId();
        }

        return "redirect:/course/all";
    }

    @GetMapping("/edit/{testId}")
    private String showEditTestForm(@PathVariable("testId") Long testId, Model model) {
        Optional<Test> optionalTest = testRepository.findById(testId);

        if (optionalTest.isPresent()) {
            model.addAttribute("test", optionalTest.get());
            model.addAttribute("allCourses", courseRepository.findAll());
            model.addAttribute("purpose", "Edit a test");
            return "/test/addForm";
        }

        return "redirect:/course/all";
    }

    @GetMapping("/{testId}/addtestitems")
    private String addTestItemsForm(@PathVariable("testId") Long testId, Model model) {
        Optional<Test> optionalTest = testRepository.findById(testId);

        if (optionalTest.isPresent()) {
            List<TestItem> allTestItemsFromTest = optionalTest.get().getTestItems();

            model.addAttribute("allTestItemsFromTest", allTestItemsFromTest);
            model.addAttribute("test", optionalTest.get());
            model.addAttribute("testitem", new TestItem());

            return "/test/addTestItemsForm";
        }

        return "redirect:/course/all";
    }

    @PostMapping("/addtestitem")
    private String saveTestItem(@ModelAttribute("testitem") TestItem testItem,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "/test/addTestItemsForm";
        }

        testItemRepository.save(testItem);
        return "/test/addTestItemsForm";
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
