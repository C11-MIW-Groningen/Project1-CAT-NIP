package com.project1CATNIP.CATNIP.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

class StudentTest {

    Program softwareEngineering = new Program();
    Cohort se11 = new Cohort();
    Student student = new Student();

    com.project1CATNIP.CATNIP.model.Test testProgramming = new com.project1CATNIP.CATNIP.model.Test();
    com.project1CATNIP.CATNIP.model.Test testDatabases = new com.project1CATNIP.CATNIP.model.Test();

    Course programmming = new Course();
    Course databases = new Course();
    Course oop = new Course();

    TestAttempt testAttempt1 = new TestAttempt();
    TestAttempt testAttempt2 = new TestAttempt();
    TestAttempt testAttempt3 = new TestAttempt();
    TestAttempt testAttempt4 = new TestAttempt();
    TestAttempt testAttempt5 = new TestAttempt();
    TestAttempt testAttempt6 = new TestAttempt();

    List<TestAttempt> testAttempts = new ArrayList<>();

    private void setTestAttempts(double[] numbers) {

        se11.setProgram(softwareEngineering);
        programmming.setProgram(softwareEngineering);
        databases.setProgram(softwareEngineering);
        oop.setProgram(softwareEngineering);
        student.setCohort(se11);

        testProgramming.setCourse(programmming);
        testDatabases.setCourse(databases);

        testAttempt1.setAttemptResult(numbers[0]);
        testAttempt1.setTest(testProgramming);
        testAttempt1.setStudent(student);
        testAttempt2.setAttemptResult(numbers[1]);
        testAttempt2.setTest(testProgramming);
        testAttempt2.setStudent(student);
        testAttempt3.setAttemptResult(numbers[2]);
        testAttempt3.setTest(testProgramming);
        testAttempt3.setStudent(student);
        testAttempt4.setAttemptResult(numbers[3]);
        testAttempt4.setTest(testProgramming);
        testAttempt4.setStudent(student);
        testAttempt5.setAttemptResult(numbers[4]);
        testAttempt5.setTest(testDatabases);
        testAttempt5.setStudent(student);
        testAttempt6.setAttemptResult(numbers[5]);
        testAttempt6.setTest(testDatabases);
        testAttempt6.setStudent(student);

        testAttempts.add(testAttempt1);
        testAttempts.add(testAttempt2);
        testAttempts.add(testAttempt3);
        testAttempts.add(testAttempt4);
        testAttempts.add(testAttempt5);
        testAttempts.add(testAttempt6);

        student.setTestAttempt(testAttempts);

    }

    @Test
    void getHighestTestAttemptForDatabases() {
        //Arrange
        //Six grades: 1-4 for course Programming, the last 5-6 for course Databases
        double[] grades = {5.5, 10.0, 1.0, 9.2, 5.5, 9.0};
        setTestAttempts(grades);

        //Highest grade for course Databases: 9.0 > 5.5
        TestAttempt expectedValue = testAttempt6;

        //Act
        TestAttempt highestTestAttempt = student.getHighestTestAttemptByCourse(databases);

        //Assert
        assertEquals(expectedValue, highestTestAttempt);
    }

    @Test
    void getHighestTestAttemptForProgramming() {
        //Arrange
        //Six grades: 1-4 for course Programming, the last 5-6 for course Databases
        double[] grades = {5.5, 10.0, 1.0, 9.2, 5.5, 9.0};
        setTestAttempts(grades);

        //Highest grade for course Databases: 10.0
        TestAttempt expectedValue = testAttempt2;

        //Act
        TestAttempt highestTestAttempt = student.getHighestTestAttemptByCourse(programmming);

        //Assert
        assertEquals(expectedValue, highestTestAttempt);
    }

    @Test
    void lowestAttemptForProgramming() {
        //Arrange
        //Six grades: 1-4 for course Programming, the last 5-6 for course Databases
        double[] grades = {5.5, 10.0, 1.0, 9.2, 5.5, 9.0};
        setTestAttempts(grades);

        //Highest grade for course Programming: 10.0; Attempt 3: 1.0;
        TestAttempt expectedValue = testAttempt3;

        //Act
        TestAttempt highestTestAttempt = student.getHighestTestAttemptByCourse(programmming);

        //Assert
        assertNotEquals(expectedValue, highestTestAttempt);
    }

    @Test
    void getAttemptWithSameScore() {
        //Arrange
        //Six grades: 1-4 for course Programming, the last 5-6 for course Databases
        double[] grades = {10.0, 10.0, 1.0, 9.2, 5.5, 9.0};
        setTestAttempts(grades);

        //Highest grade for course Programming: 10.0, but this goes for both test attempt 1 and 2;
        //We check: attemptScore >= highScore, so we expect it to return the newTest, so in this case, the 2nd attempt
        TestAttempt expectedValue = testAttempt2;

        //Act
        TestAttempt highestTestAttempt = student.getHighestTestAttemptByCourse(programmming);

        //Assert
        assertEquals(expectedValue, highestTestAttempt);
    }

    @Test
    void getNonExistentTestAttemptForCourse() {
        //Arrange
        //Six grades: 1-4 for course Programming, the last 5-6 for course Databases
        double[] grades = {10.0, 10.0, 1.0, 9.2, 5.5, 9.0};
        setTestAttempts(grades);

        //Non-existent test attempt returns an empty test attempt for a student, which has attemptResult 0.0
        double expectedResult = 0.0;

        //Act
        TestAttempt highestTestAttempt = student.getHighestTestAttemptByCourse(oop);
        double highestAttempt = highestTestAttempt.getAttemptResult();

        //Assert
        assertEquals(expectedResult, highestAttempt);
    }
}