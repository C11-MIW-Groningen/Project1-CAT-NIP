package com.project1CATNIP.CATNIP.controller;

/*
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Seeds the database.
 */

import com.project1CATNIP.CATNIP.model.*;
import com.project1CATNIP.CATNIP.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final AssignmentRepository assignmentRepository;

    private final TestAttemptRepository testAttemptRepository;

    private final TestItemRepository testItemRepository;

    private final PasswordEncoder passwordEncoder;

    private final MIWUserRepository miwUserRepository;

    @GetMapping("/seed")
    private String seedDatabase() {

        testItemRepository.deleteAll();
        testAttemptRepository.deleteAll();
        assignmentRepository.deleteAll();
        testRepository.deleteAll();
        courseRepository.deleteAll();
        cohortRepository.deleteAll();
        teacherRepository.deleteAll();
        programRepository.deleteAll();

        Teacher janna = new Teacher();
        janna.setFirstName("Janna");
        janna.setInfixName("");
        janna.setLastName("Scherpenzeel");
        janna.setEmailAddress("j.scherpenzeel@cat-nip.nl");
        teacherRepository.save(janna);

        Teacher mark = new Teacher();
        mark.setFirstName("Mark");
        mark.setInfixName("Van der");
        mark.setLastName("Berg");
        mark.setEmailAddress("m.vd.berg@cat-nip.nl");
        teacherRepository.save(mark);

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
        programming.setCourseName("Programming");
        programming.setProgram(softwareEngineering);
        programming.setTeacher(mark);
        courseRepository.save(programming);

        Course databases = new Course();
        databases.setCourseName("Databases");
        databases.setProgram(softwareEngineering);
        databases.setTeacher(janna);
        courseRepository.save(databases);

        Student student1 = new Student();
        student1.setFirstName("Henk");
        student1.setInfixName("de");
        student1.setLastName("Vries");
        student1.setEmailAddress("h.de.vries@hmail.com");
        student1.setCohort(se11);
        if (!studentRepository.findAll().contains(student1)) {
            studentRepository.save(student1);
        }


        Student student2 = new Student();
        student2.setFirstName("Angela");
        student2.setLastName("Jongsma");
        student2.setEmailAddress("ajongsma@hmail.com");
        student2.setCohort(se11);
        studentRepository.save(student2);
        if (!studentRepository.findAll().contains(student2)) {
            studentRepository.save(student2);
        }

        Test test1 = new Test();
        test1.setNameTest("Exam BMI application");
        test1.setCourse(programming);
        testRepository.save(test1);

        Test test2 = new Test();
        test2.setNameTest("Re-sit Dice application");
        test2.setCourse(programming);
        testRepository.save(test2);

        Test test3 = new Test();
        test3.setNameTest("MySQL Queries: bedrijf");
        test3.setCourse(databases);
        testRepository.save(test3);

        Test test4 = new Test();
        test4.setNameTest("MariaDB: Woningen");
        test4.setCourse(databases);
        testRepository.save(test4);

        TestItem test1item1 = new TestItem();
        test1item1.setItemNumberForTest(1);
        test1item1.setDescription("Onderdeel 1");
        test1item1.setMaxPoints(5);
        test1item1.setTest(test1);
        testItemRepository.save(test1item1);

        TestItem test1item2 = new TestItem();
        test1item2.setItemNumberForTest(2);
        test1item2.setDescription("Onderdeel 2");
        test1item2.setMaxPoints(10);
        test1item2.setTest(test1);
        testItemRepository.save(test1item2);

        TestItem test1item3 = new TestItem();
        test1item3.setItemNumberForTest(3);
        test1item3.setDescription("Onderdeel 3");
        test1item3.setMaxPoints(15);
        test1item3.setTest(test1);
        testItemRepository.save(test1item3);

        TestItem test2item1 = new TestItem();
        test2item1.setItemNumberForTest(1);
        test2item1.setDescription("Vraag 1");
        test2item1.setMaxPoints(4);
        test2item1.setTest(test2);
        testItemRepository.save(test2item1);

        TestItem test2item2 = new TestItem();
        test2item2.setItemNumberForTest(2);
        test2item2.setDescription("Vraag 2");
        test2item2.setMaxPoints(8);
        test2item2.setTest(test2);
        testItemRepository.save(test2item2);

        TestItem test2item3 = new TestItem();
        test2item3.setItemNumberForTest(3);
        test2item3.setDescription("Vraag 3");
        test2item3.setMaxPoints(12);
        test2item3.setTest(test2);
        testItemRepository.save(test2item3);

        TestItem test3item1 = new TestItem();
        test3item1.setItemNumberForTest(1);
        test3item1.setDescription("Onderwerp 1");
        test3item1.setMaxPoints(11);
        test3item1.setTest(test3);
        testItemRepository.save(test3item1);

        TestItem test3item2 = new TestItem();
        test3item2.setItemNumberForTest(2);
        test3item2.setDescription("Onderwerp 2");
        test3item2.setMaxPoints(6);
        test3item2.setTest(test3);
        testItemRepository.save(test3item2);

        TestItem test3item3 = new TestItem();
        test3item3.setItemNumberForTest(3);
        test3item3.setDescription("Onderwerp 3");
        test3item3.setMaxPoints(14);
        test3item3.setTest(test3);
        testItemRepository.save(test3item3);

        TestItem test4item1 = new TestItem();
        test4item1.setItemNumberForTest(1);
        test4item1.setDescription("Vraag 1");
        test4item1.setMaxPoints(10);
        test4item1.setTest(test4);
        testItemRepository.save(test4item1);

        TestItem test4item2 = new TestItem();
        test4item2.setItemNumberForTest(2);
        test4item2.setDescription("Vraag 2");
        test4item2.setMaxPoints(12);
        test4item2.setTest(test4);
        testItemRepository.save(test4item2);

        TestItem test4item3 = new TestItem();
        test4item3.setItemNumberForTest(3);
        test4item3.setDescription("Vraag 3");
        test4item3.setMaxPoints(12);
        test4item3.setTest(test4);
        testItemRepository.save(test4item3);

        TestAttempt test1student1attempt1 = new TestAttempt();
        test1student1attempt1.setAttemptDate(LocalDate.of(2022, 2, 3));
        test1student1attempt1.setStudent(student1);
        test1student1attempt1.setTest(test1);
        test1student1attempt1.setAttemptResult(9.0);
        testAttemptRepository.save(test1student1attempt1);

        TestAttempt test2student2attempt2 = new TestAttempt();
        test2student2attempt2.setAttemptDate(LocalDate.of(2022, 2, 3));
        test2student2attempt2.setStudent(student1);
        test2student2attempt2.setTest(test2);
        test2student2attempt2.setAttemptResult(8.7);
        testAttemptRepository.save(test2student2attempt2);

        TestAttempt test1student2attempt1 = new TestAttempt();
        test1student2attempt1.setAttemptDate(LocalDate.of(2022, 2, 3));
        test1student2attempt1.setStudent(student2);
        test1student2attempt1.setTest(test1);
        test1student2attempt1.setAttemptResult(6.1);
        testAttemptRepository.save(test1student2attempt1);

        TestAttempt test1student2attempt2 = new TestAttempt();
        test1student2attempt2.setAttemptDate(LocalDate.of(2022, 3, 3));
        test1student2attempt2.setStudent(student2);
        test1student2attempt2.setTest(test2);
        test1student2attempt2.setAttemptResult(9.2);
        testAttemptRepository.save(test1student2attempt2);

        TestAttempt ta5 = new TestAttempt();
        ta5.setAttemptDate(LocalDate.of(2023, 4, 5));
        ta5.setStudent(student1);
        ta5.setTest(test3);
        ta5.setAttemptResult(7.2);
        testAttemptRepository.save(ta5);

        TestAttempt ta6 = new TestAttempt();
        ta6.setAttemptDate(LocalDate.of(2023, 4, 5));
        ta6.setStudent(student2);
        ta6.setTest(test4);
        ta6.setAttemptResult(8.0);
        testAttemptRepository.save(ta6);

        Assignment assignment1 = new Assignment();
        assignment1.setAssignmentName("Meetkunde");
        assignment1.setDayPart(1);
        assignment1.setAssignmentNumber(1);
        assignment1.setCourse(programming);
        assignmentRepository.save(assignment1);

        Assignment assignment2 = new Assignment();
        assignment2.setAssignmentName("Top 2000");
        assignment2.setDayPart(2);
        assignment2.setAssignmentNumber(1);
        assignment2.setCourse(databases);
        assignmentRepository.save(assignment2);

        MIWUser teacherUser = new MIWUser();
        teacherUser.setUsername("teacher");
        teacherUser.setPassword(passwordEncoder.encode("teacherpw"));
        teacherUser.setTeacher(true);
        if (miwUserRepository.findByUsername("teacher").isEmpty()) {
            miwUserRepository.save(teacherUser);
        }

        MIWUser studentUser = new MIWUser();
        studentUser.setUsername("student");
        studentUser.setStudent(student1);
        studentUser.setPassword(passwordEncoder.encode("studentpw"));
        if (miwUserRepository.findByUsername("student").isEmpty()) {
            miwUserRepository.save(studentUser);
        }

        MIWUser studentUser2 = new MIWUser();
        studentUser2.setUsername("angela");
        studentUser2.setStudent(student2);
        studentUser2.setPassword(passwordEncoder.encode("studentpw"));
        if (miwUserRepository.findByUsername("angela").isEmpty()) {
            miwUserRepository.save(studentUser2);
        }

        return "redirect:/program";
    }
}
