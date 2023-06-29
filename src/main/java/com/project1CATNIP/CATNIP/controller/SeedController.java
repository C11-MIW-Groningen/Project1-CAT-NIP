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
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SeedController {

    private final int INDEX_ADMIN = 0;

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

        List<MIWUser> usersToRemove = miwUserRepository.findAll();
        usersToRemove.remove(INDEX_ADMIN);

        for (MIWUser user : usersToRemove) {
            miwUserRepository.delete(user);
        }

        testItemRepository.deleteAll();
        testAttemptRepository.deleteAll();
        assignmentRepository.deleteAll();
        testRepository.deleteAll();
        courseRepository.deleteAll();
        studentRepository.deleteAll();
        cohortRepository.deleteAll();
        teacherRepository.deleteAll();
        programRepository.deleteAll();

        Teacher janna = new Teacher();
        janna.setFirstName("Janna");
        janna.setLastName("Scherpenzeel");
        janna.setEmailAddress("j.scherpenzeel@cat-nip.nl");
        teacherRepository.save(janna);

        Teacher mark = new Teacher();
        mark.setFirstName("Mark");
        mark.setInfixName("Van der");
        mark.setLastName("Berg");
        mark.setEmailAddress("m.vd.berg@cat-nip.nl");
        teacherRepository.save(mark);

        Teacher hendrik = new Teacher();
        hendrik.setFirstName("Hendrik");
        hendrik.setLastName("Jansma");
        hendrik.setEmailAddress("h.jansma@cat-nip.nl");
        teacherRepository.save(hendrik);

        Teacher sterre = new Teacher();
        sterre.setFirstName("Sterre");
        sterre.setInfixName("De");
        sterre.setLastName("Laat");
        sterre.setEmailAddress("s.d.laat@cat-nip.nl");
        teacherRepository.save(sterre);

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

        Student henk = new Student();
        henk.setFirstName("Henk");
        henk.setInfixName("de");
        henk.setLastName("Vries");
        henk.setEmailAddress("h.d.vries@st.hanze.nl");
        henk.setCohort(se11);
        studentRepository.save(henk);

        Student angela = new Student();
        angela.setFirstName("Angela");
        angela.setLastName("Jongsma");
        angela.setEmailAddress("a.jongsma@st.hanze.nl");
        angela.setCohort(se11);
        studentRepository.save(angela);

        Student anton = new Student();
        anton.setFirstName("Anton");
        anton.setInfixName("Van de");
        anton.setLastName("Berg");
        anton.setEmailAddress("a.vd.berg@st.hanze.nl");
        anton.setCohort(se11);
        studentRepository.save(anton);

        Student leonie = new Student();
        leonie.setFirstName("Leonie");
        leonie.setLastName("Hendriksen");
        leonie.setEmailAddress("l.hendriksen@st.hanze.nl");
        leonie.setCohort(se11);
        studentRepository.save(leonie);

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
        test1student1attempt1.setStudent(henk);
        test1student1attempt1.setTest(test1);
        test1student1attempt1.setAttemptResult(9.0);
        testAttemptRepository.save(test1student1attempt1);

        TestAttempt test2student2attempt2 = new TestAttempt();
        test2student2attempt2.setAttemptDate(LocalDate.of(2022, 2, 3));
        test2student2attempt2.setStudent(henk);
        test2student2attempt2.setTest(test2);
        test2student2attempt2.setAttemptResult(8.7);
        testAttemptRepository.save(test2student2attempt2);

        TestAttempt test1student2attempt1 = new TestAttempt();
        test1student2attempt1.setAttemptDate(LocalDate.of(2022, 2, 3));
        test1student2attempt1.setStudent(angela);
        test1student2attempt1.setTest(test1);
        test1student2attempt1.setAttemptResult(6.1);
        testAttemptRepository.save(test1student2attempt1);

        TestAttempt test1student2attempt2 = new TestAttempt();
        test1student2attempt2.setAttemptDate(LocalDate.of(2022, 3, 3));
        test1student2attempt2.setStudent(angela);
        test1student2attempt2.setTest(test2);
        test1student2attempt2.setAttemptResult(9.2);
        testAttemptRepository.save(test1student2attempt2);

        TestAttempt ta5 = new TestAttempt();
        ta5.setAttemptDate(LocalDate.of(2023, 4, 5));
        ta5.setStudent(henk);
        ta5.setTest(test3);
        ta5.setAttemptResult(7.2);
        testAttemptRepository.save(ta5);

        TestAttempt ta6 = new TestAttempt();
        ta6.setAttemptDate(LocalDate.of(2023, 4, 5));
        ta6.setStudent(angela);
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

        MIWUser studentHenk = new MIWUser();
        studentHenk.setUsername("henk");
        studentHenk.setStudent(henk);
        studentHenk.setPassword(passwordEncoder.encode("studentpw"));
        if (miwUserRepository.findByUsername("henk").isEmpty()) {
            miwUserRepository.save(studentHenk);
        }

        MIWUser studentAngela = new MIWUser();
        studentAngela.setUsername("angela");
        studentAngela.setStudent(angela);
        studentAngela.setPassword(passwordEncoder.encode("studentpw"));
        if (miwUserRepository.findByUsername("angela").isEmpty()) {
            miwUserRepository.save(studentAngela);
        }

        MIWUser studentAnton = new MIWUser();
        studentAnton.setUsername("anton");
        studentAnton.setStudent(anton);
        studentAnton.setPassword(passwordEncoder.encode("studentpw"));
        if (miwUserRepository.findByUsername("anton").isEmpty()) {
            miwUserRepository.save(studentAnton);
        }

        MIWUser studentLeonie = new MIWUser();
        studentLeonie.setUsername("leonie");
        studentLeonie.setStudent(leonie);
        studentLeonie.setPassword(passwordEncoder.encode("studentpw"));
        if (miwUserRepository.findByUsername("leonie").isEmpty()) {
            miwUserRepository.save(studentLeonie);
        }

        MIWUser teacherJanna = new MIWUser();
        teacherJanna.setUsername("janna");
        teacherJanna.setTeacher(janna);
        teacherJanna.setIsTeacher(true);
        teacherJanna.setPassword(passwordEncoder.encode("teacherpw"));
        if (miwUserRepository.findByUsername("janna").isEmpty()) {
            miwUserRepository.save(teacherJanna);
        }

        MIWUser teacherMark = new MIWUser();
        teacherMark.setUsername("mark");
        teacherMark.setTeacher(mark);
        teacherMark.setIsTeacher(true);
        teacherMark.setPassword(passwordEncoder.encode("teacherpw"));
        if (miwUserRepository.findByUsername("mark").isEmpty()) {
            miwUserRepository.save(teacherMark);
        }

        MIWUser teacherHendrik = new MIWUser();
        teacherHendrik.setUsername("hendrik");
        teacherHendrik.setTeacher(hendrik);
        teacherHendrik.setIsTeacher(true);
        teacherHendrik.setPassword(passwordEncoder.encode("teacherpw"));
        if (miwUserRepository.findByUsername("hendrik").isEmpty()) {
            miwUserRepository.save(teacherHendrik);
        }

        MIWUser teacherSterre = new MIWUser();
        teacherSterre.setUsername("sterre");
        teacherSterre.setTeacher(sterre);
        teacherSterre.setIsTeacher(true);
        teacherSterre.setPassword(passwordEncoder.encode("teacherpw"));
        if (miwUserRepository.findByUsername("sterre").isEmpty()) {
            miwUserRepository.save(teacherSterre);
        }

        return "redirect:/program";
    }
}
