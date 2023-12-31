package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.Assignment;
import com.project1CATNIP.CATNIP.repository.AssignmentRepository;
import com.project1CATNIP.CATNIP.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handles all interactions of assignments.
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/assignment")
public class AssignmentController {

    private final String ASSIGNMENT_ADD_FORM = "/assignment/assignmentAddForm";

    private final AssignmentRepository assignmentRepository;

    private final CourseRepository courseRepository;

    @GetMapping("/add")
    private String addAssignmentForm(Model model) {
        model.addAttribute("assignment", new Assignment());
        model.addAttribute("allCourses", courseRepository.findAll());
        model.addAttribute("purpose", "Add an assignment");

        return ASSIGNMENT_ADD_FORM;
    }

    @PostMapping("/add")
    private String saveAssignment(@ModelAttribute("assignment") Assignment assignmentToAdd, BindingResult result,
                                  Model model) {
        try {
            if (result.hasErrors()) {
                return "/assignment/assignmentAddForm";
            }

            assignmentRepository.save(assignmentToAdd);
            return "redirect:/assignment/add";

        } catch (DataIntegrityViolationException exception) {
            model.addAttribute("failure", "This assignment already exists. Try again");
            return "/assignment/assignmentAddForm";
        }
    }

    @GetMapping("/delete/{assignmentId}")
    private String deleteAssignment(@PathVariable("assignmentId") Long assignmentId) {
        Optional<Assignment> assignmentToDelete = assignmentRepository.findById(assignmentId);

        if (assignmentToDelete.isPresent()) {
            assignmentRepository.delete(assignmentToDelete.get());
            return "redirect:/course/details/" + assignmentToDelete.get().getCourse().getCourseId();
        }

        return "redirect:/course/all";
    }

    @GetMapping("/edit/{assignmentId}")
    private String showEditAssignmentForm(@PathVariable("assignmentId") Long assignmentId, Model model) {
        Optional<Assignment> optionalAssignment = assignmentRepository.findById(assignmentId);

        if (optionalAssignment.isPresent()) {
            model.addAttribute("assignment", optionalAssignment.get());
            model.addAttribute("allCourses", courseRepository.findAll());
            model.addAttribute("purpose", "Edit an assignment");
            return "/assignment/assignmentAddForm";
        }

        return "redirect:/course/all";
    }
}
