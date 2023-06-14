package com.project1CATNIP.CATNIP.controller;

/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.Cohort;
import com.project1CATNIP.CATNIP.model.Program;
import com.project1CATNIP.CATNIP.model.Student;
import com.project1CATNIP.CATNIP.model.Teacher;
import com.project1CATNIP.CATNIP.repository.CohortRepository;
import com.project1CATNIP.CATNIP.repository.ProgramRepository;
import com.project1CATNIP.CATNIP.repository.StudentRepository;
import com.project1CATNIP.CATNIP.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class SeedController {

    private final TeacherRepository teacherRepository;
    private final ProgramRepository programRepository;
    private final CohortRepository cohortRepository;
    private final StudentRepository studentRepository;

    @GetMapping("/seed")
    private String seedDatabase() {

        Teacher wally = new Teacher();
        wally.setFirstName("Walther");
        wally.setInfixName("De");
        wally.setLastName("Wit");
        wally.setEmailAddress("w.d.wit@cat-nip.nl");
        teacherRepository.save(wally);

        Teacher piet = new Teacher();
        piet.setFirstName("Pieter");
        piet.setInfixName("Van de");
        piet.setLastName("Breedband");
        piet.setEmailAddress("p.vd.breedband@cat-nip.nl");
        teacherRepository.save(piet);

        Program softwareEngineering = new Program();
        softwareEngineering.setNameProgram("Software Engineering");
        programRepository.save(softwareEngineering);

        Cohort se11 = new Cohort();
        se11.setNumber(11);
        se11.setProgram(softwareEngineering);
        se11.setStartDate(LocalDate.of(2023, 4, 5));
        cohortRepository.save(se11);

        Student student1 = new Student();
        student1.setFirstNameStudent("Henk");
        student1.setInfixNameStudent("de");
        student1.setLastNameStudent("Vries");
        student1.setEmailAddressStudent("h.de.vries@hmail.com");
        student1.setCohort(se11);
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setFirstNameStudent("Angela");
        student2.setLastNameStudent("Jongsma");
        student2.setEmailAddressStudent("ajongsma@hmail.com");
        student2.setCohort(se11);
        studentRepository.save(student2);

        return "redirect:/program";
    }
}
