package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.Program;
import com.project1CATNIP.CATNIP.repository.ProgramRepository;
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
@RequestMapping("/program")
public class ProgramController {
    private final ProgramRepository programRepository;

    @GetMapping({"/", "", "/all"})
    private String showAllPrograms(Model model) {
        model.addAttribute("allPrograms", programRepository.findAll());

        return "programOverview";
    }

    @GetMapping("/add")
    private String addProgramForm(Model model) {
        model.addAttribute("program", new Program());

        return "programAddForm";
    }

    @PostMapping("/add")
    private String saveProgram(@ModelAttribute("program") Program programToAdd, BindingResult result) {

        if (!result.hasErrors()) {
            programRepository.save(programToAdd);
        }

        return "redirect:/program/all";
    }

    @GetMapping("/delete/{programId}")
    private String deleteProgram(@PathVariable("programId") Long programId) {
        Optional<Program> programToDelete = programRepository.findById(programId);

        if (programToDelete.isPresent()) {
            programRepository.delete(programToDelete.get());
        }

        return "redirect:/program/all";
    }
    //TODO Delete validatie

    @GetMapping("/edit/{programId}")
    private String showEditCourseForm(@PathVariable("programId") Long programId, Model model) {
        Optional<Program> optionalProgram = programRepository.findById(programId);

        if (optionalProgram.isPresent()) {
            model.addAttribute("program", optionalProgram.get());
            return "programAddForm";
        }

        return "redirect:/program/all";
    }
}
