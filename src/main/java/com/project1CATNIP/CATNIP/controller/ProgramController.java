package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.Cohort;
import com.project1CATNIP.CATNIP.model.Program;
import com.project1CATNIP.CATNIP.repository.CohortRepository;
import com.project1CATNIP.CATNIP.repository.CourseRepository;
import com.project1CATNIP.CATNIP.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handle all program interactions.
 */

@Controller
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramRepository programRepository;

    private final CourseRepository courseRepository;

    private final CohortRepository cohortRepository;

    @GetMapping({"/", "", "/program", "/program/all"})
    private String showAllPrograms(Model model) {
        model.addAttribute("allPrograms", programRepository.findAll());

        return "/program/programOverview";
    }

    @GetMapping("/program/add")
    private String addProgramForm(Model model) {
        model.addAttribute("program", new Program());

        return "/program/programAddForm";
    }

    @PostMapping("/program/add")
    private String saveProgram(@ModelAttribute("program") Program programToAdd, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/program/programAddForm";
        }

        programToAdd.setNameProgram(programToAdd.getNameProgram());
        programRepository.save(programToAdd);
        String successMessage = "Program added successfully.";
        model.addAttribute("success", successMessage);
        return "/program/programAddForm";
    }

    @GetMapping("/program/delete/{programId}")
    private String deleteProgram(@PathVariable("programId") Long programId) {
        Optional<Program> programToDelete = programRepository.findById(programId);

        if (programToDelete.isPresent()) {
            Program program = programToDelete.get();
            List<Cohort> cohortsFromProgram = cohortRepository.findByProgram(program);

            for (Cohort cohort : cohortsFromProgram) {
                cohort.setProgram(null);
                cohortsFromProgram.remove(cohort);
            }

            programRepository.delete(program);
        }

        return "redirect:/program/all";
    }

    @GetMapping("/program/edit/{programId}")
    private String showEditProgramForm(@PathVariable("programId") Long programId, Model model) {
        Optional<Program> optionalProgram = programRepository.findById(programId);

        if (optionalProgram.isPresent()) {
            model.addAttribute("program", optionalProgram.get());
            return "/program/programAddForm";
        }

        return "redirect:/program/all";
    }

    @GetMapping("/program/details/{programId}")
    private String showProgramDetails(@PathVariable("programId") Long programId, Model model) {
        Optional<Program> optionalProgram = programRepository.findById(programId);

        if (optionalProgram.isPresent()) {
            Program program = optionalProgram.get();
            model.addAttribute("program", program);
            model.addAttribute("coursesFromProgram", courseRepository.findByProgram(program));
            return "/program/programDetails";
        }

        return "redirect:/program/all";
    }
}
