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

            return "/cohort/cohortAddForm";
        }

        @PostMapping("/add")
        private String saveCohort(@ModelAttribute("cohort") Cohort cohortToAdd, BindingResult result, Model model) {
            if (result.hasErrors()) {
                return "/cohort/cohortAddForm";
            }

            cohortRepository.save(cohortToAdd);
            String successMessage = "Cohort added successfully.";
            model.addAttribute("success", successMessage);

            return "redirect:/cohort/add";
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
                return "/cohort/cohortAddForm";
            }

            return "redirect:/cohort/all";
        }

        @GetMapping("/details/{cohortId}")
        private String showCohortDetails(@PathVariable("cohortId") Long cohortId, Model model) {
            Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);

            if (optionalCohort.isPresent()) {
                model.addAttribute("cohort", optionalCohort.get());
                List<Student> studentsWithoutCohort = studentRepository.findAll();
                studentsWithoutCohort.removeAll(optionalCohort.get().getStudents());
                model.addAttribute("allStudents", studentsWithoutCohort);
                model.addAttribute("enrollment",
                        CohortEnrollmentDTO.builder().cohort(optionalCohort.get()).build());
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
