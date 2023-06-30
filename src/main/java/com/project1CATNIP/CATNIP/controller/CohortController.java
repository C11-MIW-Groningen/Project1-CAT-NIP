package com.project1CATNIP.CATNIP.controller;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handles all cohort interactions.
 */

import com.project1CATNIP.CATNIP.dto.CohortEnrollmentDTO;
import com.project1CATNIP.CATNIP.model.Cohort;
import com.project1CATNIP.CATNIP.model.Student;
import com.project1CATNIP.CATNIP.repository.CohortRepository;
import com.project1CATNIP.CATNIP.repository.ProgramRepository;
import com.project1CATNIP.CATNIP.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            model.addAttribute("purpose", "Add a cohort");

            return "/cohort/cohortAddForm";
        }

        @PostMapping("/add")
        private String saveCohort(@ModelAttribute("cohort") Cohort cohortToAdd, BindingResult result, Model model) {
            try {
                if (result.hasErrors()) {
                    return "/cohort/cohortAddForm";
                }
                cohortRepository.save(cohortToAdd);

                return "redirect:/cohort/all";

            } catch (Exception exception) {
                System.err.println(exception.getMessage());
                String failure = "This cohort already exists. Select a different number.";
                model.addAttribute("failure", failure);
                return "/cohort/cohortAddForm";
            }
        }

        @PostMapping("/edit")
        private String saveEditedCohort(@ModelAttribute("cohort") Cohort cohortToEdit, BindingResult result, Model model) {
            try {
                if (result.hasErrors()) {
                    return "/cohort/cohortAddForm";
                }
                cohortRepository.save(cohortToEdit);
                return "redirect:/cohort/all";

            } catch (Exception exception) {
                System.err.println(exception.getMessage());
                String failure = "This cohort already exists. Select a different number.";
                model.addAttribute("failure", failure);
                return "/cohort/cohortAddForm";
            }
        }

        @GetMapping("/delete/{cohortId}")
        private String deleteCohort(@PathVariable("cohortId") Long cohortId) {
            Optional<Cohort> cohortToDelete = cohortRepository.findById(cohortId);

            if (cohortToDelete.isPresent()) {
                Cohort cohort = cohortToDelete.get();
                List<Student> studentsFromCohort = studentRepository.findStudentsByCohort(cohort);

                for (Student student : studentsFromCohort) {
                    student.setCohort(null);
                    studentRepository.save(student);
                }
                cohortRepository.delete(cohort);
            }

            return "redirect:/cohort/all";
        }

        @GetMapping("/edit/{cohortId}")
        private String showEditCohortForm(@PathVariable("cohortId") Long cohortId, Model model) {
            Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);

            if (optionalCohort.isPresent()) {
                model.addAttribute("cohort", optionalCohort.get());
                model.addAttribute("allPrograms", programRepository.findAll());
                model.addAttribute("purpose", "Edit a cohort");
                return "/cohort/cohortAddForm";
            }

            return "redirect:/cohort/all";
        }

        @GetMapping("/details/{cohortId}")
        private String showCohortDetails(@PathVariable("cohortId") Long cohortId, Model model) {
            Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);

            if (optionalCohort.isPresent()) {
                Cohort thisCohort = optionalCohort.get();
                List<Student> studentsWithoutCohort = studentRepository.findAll();
                studentsWithoutCohort.removeAll(thisCohort.getStudents());

                model.addAttribute("cohort", thisCohort);
                model.addAttribute("allStudents", studentsWithoutCohort);
                model.addAttribute("enrollment",
                        CohortEnrollmentDTO.builder().cohort(thisCohort).build());

                return "/cohort/cohortDetails";
            }

            return "redirect:/cohort/all";
        }

        @PostMapping("/addStudent")
        private String addStudentToCohort(@ModelAttribute("enrollment") CohortEnrollmentDTO cohortEnrollmentDTO,
                                          BindingResult result) {
            if (result.hasErrors()) {
                return "redirect:/cohort/all";
            }

            cohortEnrollmentDTO.getCohort().addStudent(cohortEnrollmentDTO.getStudent());
            cohortRepository.save(cohortEnrollmentDTO.getCohort());

            cohortEnrollmentDTO.getStudent().setCohort(cohortEnrollmentDTO.getCohort());
            studentRepository.save(cohortEnrollmentDTO.getStudent());

            return "redirect:/cohort/details/" + cohortEnrollmentDTO.getCohort().getCohortId();
        }

        @GetMapping("/remove/{cohortId}/{studentId}")
        private String removeStudentFromCohort(@PathVariable("cohortId") Long cohortId,
                                               @PathVariable("studentId") Long studentId) {
            Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);
            Optional<Student> optionalStudent = studentRepository.findById(studentId);

            if (optionalCohort.isEmpty() || optionalStudent.isEmpty()) {
                return "redirect:/cohort/all";
            }

            optionalCohort.get().removeStudent(optionalStudent.get());
            cohortRepository.save(optionalCohort.get());

            optionalStudent.get().setCohort(null);
            studentRepository.save(optionalStudent.get());

            return "redirect:/cohort/details/" + cohortId;
        }
}
