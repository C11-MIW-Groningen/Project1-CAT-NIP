package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.*;
import com.project1CATNIP.CATNIP.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestAttemptControllerTest {

    Program program;
    Cohort cohort;
    Course course;
    com.project1CATNIP.CATNIP.model.Test test1;
    com.project1CATNIP.CATNIP.model.Test test2;
    Student student;
    List<TestAttempt> testAttempts;
    TestAttemptRepository testAttemptRepository;
    StudentRepository studentRepository;
    CourseRepository courseRepository;
    CohortRepository cohortRepository;
    TestRepository testRepository;

    @BeforeEach
    void setAttributes() {
        program = new Program();

        cohort = new Cohort();
        cohort.setProgram(program);

        course = new Course();

        test1 = new com.project1CATNIP.CATNIP.model.Test();
        test1.setCourse(course);
        test2 = new com.project1CATNIP.CATNIP.model.Test();
        test2.setCourse(course);

        student = new Student();
        student.setCohort(cohort);

        testAttempts = getTestAttemptList(student, test1, test2);

        // Mock-repositories aanmaken
        testAttemptRepository = mock(TestAttemptRepository.class);
        studentRepository = mock(StudentRepository.class);
        courseRepository = mock(CourseRepository.class);
        cohortRepository = mock(CohortRepository.class);
        testRepository = mock(TestRepository.class);
    }

    private List<TestAttempt> getTestAttemptList(
            Student student,
            com.project1CATNIP.CATNIP.model.Test test1,
            com.project1CATNIP.CATNIP.model.Test test2) {
        TestAttempt attempt1 = new TestAttempt();
        attempt1.setAttemptResult(5.5);
        attempt1.setStudent(student);
        attempt1.setTest(test1);

        TestAttempt attempt2 = new TestAttempt();
        attempt2.setAttemptResult(10);
        attempt2.setStudent(student);
        attempt2.setTest(test2);

        return Arrays.asList(attempt1, attempt2);
    }

    @Test
    @DisplayName("Getting highest test attempt for a student for a course")
    void getHighestTestAttemptForCourseTest() {
        when(testAttemptRepository.findTestAttemptsByStudentAndTestIn(student, course.getTests()))
                .thenReturn(testAttempts);

        TestAttemptController testAttemptController = new TestAttemptController(
                testAttemptRepository, studentRepository, courseRepository, cohortRepository, testRepository
        );

        TestAttempt expectedResult = testAttempts.get(1);

        //Activate
        TestAttempt result = testAttemptController.getHighestTestAttemptForCourse(student, course);

        // Assert
        assertEquals(expectedResult, result);


    }

    @Test
    @DisplayName("Getting highest test attempt for a student for a course: if wrong")
    void getHighestTestAttemptForCourseTestIsWrong() {
        when(testAttemptRepository.findTestAttemptsByStudentAndTestIn(student, course.getTests()))
                .thenReturn(testAttempts);

        TestAttemptController testAttemptController = new TestAttemptController(
                testAttemptRepository, studentRepository, courseRepository, cohortRepository, testRepository
        );

        TestAttempt expectedResult = testAttempts.get(0);

        //Activate
        TestAttempt result = testAttemptController.getHighestTestAttemptForCourse(student, course);

        // Assert
        assertNotEquals(expectedResult, result);

    }



}