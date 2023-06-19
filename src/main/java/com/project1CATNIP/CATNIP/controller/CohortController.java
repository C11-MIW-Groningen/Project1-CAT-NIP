package com.project1CATNIP.CATNIP.controller;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handles all cohort interactions.
 */

import com.project1CATNIP.CATNIP.model.Cohort;
import com.project1CATNIP.CATNIP.repository.CohortRepository;
import com.project1CATNIP.CATNIP.repository.ProgramRepository;
import com.project1CATNIP.CATNIP.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cohort")
public class CohortController {

        private final CohortRepository cohortRepository;

        private final ProgramRepository programRepository;

        private final StudentRepository studentRepository;

        @GetMapping({"", "/", "/all"})
        private String showAllCohorts(Model model) {
            model.addAttribute("allCohorts", cohortRepository.findAll());

            return "/cohort/cohortOverview";
        }

        @GetMapping("/add")
        private String addCohortForm(Model model) {
            model.addAttribute("cohort", new Cohort());
            model.addAttribute("allPrograms", programRepository.findAll());

            return "/cohort/cohortAddForm";
        }

        @PostMapping("/add")
        private String saveCohort(@ModelAttribute("cohort") Cohort cohortToAdd, BindingResult result) {
            if (!result.hasErrors()) {
                cohortRepository.save(cohortToAdd);
            }

            return "redirect:/cohort/add";
            //TODO vinkje om keuze te geven om direct naar overview te gaan
        }

        @GetMapping("/delete/{cohortId}")
        private String deleteCohort(@PathVariable("cohortId") Long cohortId) {
            Optional<Cohort> cohortToDelete = cohortRepository.findById(cohortId);

            if (cohortToDelete.isPresent()) {
                cohortRepository.delete(cohortToDelete.get());
            }

            return "redirect:/cohort/all";
        }
        //TODO Delete validatie (voor alle models)

        @GetMapping("/edit/{cohortId}")
        private String showEditCohortForm(@PathVariable("cohortId") Long cohortId, Model model) {
            Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);

            if (optionalCohort.isPresent()) {
                model.addAttribute("cohort", optionalCohort.get());
                model.addAttribute("allPrograms", programRepository.findAll());
                return "/cohort/cohortAddForm";
            }

            return "redirect:/cohort/all";
        }

        @GetMapping("/details/{cohortId}")
        private String showCohortDetails(@PathVariable("cohortId") Long cohortId, Model model) {
            Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);

            if (optionalCohort.isPresent()) {
                model.addAttribute("cohort", optionalCohort.get());
                model.addAttribute("allStudents", studentRepository.findAll());
                return "/cohort/cohortDetails";
            }

            return "redirect:/cohort/all";
        }
}
