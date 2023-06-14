package com.project1CATNIP.CATNIP.controller;

/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.*;
import com.project1CATNIP.CATNIP.repository.*;
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
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TestRepository testRepository;

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

        Program dataScience = new Program();
        dataScience.setNameProgram("Data Science");
        programRepository.save(dataScience);

        Cohort se11 = new Cohort();
        se11.setNumber(11);
        se11.setProgram(softwareEngineering);
        se11.setStartDate(LocalDate.of(2023, 4, 5));
        se11.setEndDate(LocalDate.of(2023, 8, 10));
        cohortRepository.save(se11);

        Cohort ds1 = new Cohort();
        ds1.setNumber(1);
        ds1.setProgram(dataScience);
        ds1.setStartDate(LocalDate.of(2024, 2, 3));
        ds1.setEndDate(LocalDate.of(2024, 7, 2));
        cohortRepository.save(ds1);

        Course programming = new Course();
        programming.setCourseName("Progamming");
        programming.setProgram(softwareEngineering);
        programming.setTeacher(piet);
        courseRepository.save(programming);

        Course databases = new Course();
        databases.setCourseName("Databases");
        databases.setProgram(softwareEngineering);
        databases.setTeacher(wally);
        courseRepository.save(databases);

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

        Test test1 = new Test();
        test1.setNameTest("Exam BMI application");
        test1.setCourse(programming);
        testRepository.save(test1);

        Test test2 = new Test();
        test2.setNameTest("Re-exam Dice application");
        test2.setCourse(programming);
        testRepository.save(test2);

        return "redirect:/program";
    }
}
