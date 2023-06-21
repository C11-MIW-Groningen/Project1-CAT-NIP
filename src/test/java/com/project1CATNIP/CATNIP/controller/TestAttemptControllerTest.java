package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.*;
import com.project1CATNIP.CATNIP.repository.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestAttemptControllerTest {

    @Test
    void getHighestTestAttemptForCourseTest() {

        // Objects aanmaken
        Program program = new Program();

        Cohort cohort = new Cohort();
        cohort.setProgram(program);

        Course course = new Course();

        com.project1CATNIP.CATNIP.model.Test test1 = new com.project1CATNIP.CATNIP.model.Test();
        test1.setCourse(course);
        com.project1CATNIP.CATNIP.model.Test test2 = new com.project1CATNIP.CATNIP.model.Test();
        test2.setCourse(course);

        Student student = new Student();
        student.setCohort(cohort);

        List<TestAttempt> testAttempts = getTestAttemptList(student, test1, test2);

        // Mock-repositories aanmaken
        TestAttemptRepository testAttemptRepository = mock(TestAttemptRepository.class);
        StudentRepository studentRepository = mock(StudentRepository.class);
        CourseRepository courseRepository = mock(CourseRepository.class);
        CohortRepository cohortRepository = mock(CohortRepository.class);
        TestRepository testRepository = mock(TestRepository.class);

        when(testAttemptRepository.findTestAttemptsByStudentAndTestIn(student, course.getTests()))
                .thenReturn(testAttempts);

        TestAttemptController testAttemptController = new TestAttemptController(
                testAttemptRepository, studentRepository, courseRepository, cohortRepository, testRepository
        );

        // Act
        TestAttempt result = testAttemptController.getHighestTestAttemptForCourse(student, course);
        TestAttempt expectedResult1 = testAttempts.get(1); //
        TestAttempt expectedResult2 = testAttempts.get(0);

        // Assert
        assertEquals(expectedResult1, result);
        assertNotEquals(expectedResult2, result);

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

}