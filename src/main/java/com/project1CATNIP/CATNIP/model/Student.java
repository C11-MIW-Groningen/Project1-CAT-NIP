package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * A student can enroll to a cohort of a program.
 */

@Entity
@Getter @Setter
public class Student extends Person {

    @Id
    @GeneratedValue
    private Long studentId;

    @ManyToOne
    private Cohort cohort;

    @OneToMany (mappedBy = "student")
    private List<TestAttempt> testAttempt;

    public List<TestAttempt> allTestAttemptsForCourse(Course course) {
        List<TestAttempt> testAttempts = new ArrayList<>();
        for (TestAttempt attempt : this.testAttempt) {
            if (attempt.getTest().getCourse() == course) {
                testAttempts.add((attempt));
            }
        }

        return testAttempts;
    }

    public TestAttempt getHighestTestAttemptByCourse(Course course) {
        List<TestAttempt> testAttemptsForCourse = allTestAttemptsForCourse(course);
        TestAttempt highestTestAttempt = new TestAttempt();
        double highScore = 0;

        for (TestAttempt attempt : testAttemptsForCourse) {
            double attemptScore = attempt.getAttemptResult();
            if (attemptScore >= highScore) {
                highestTestAttempt = attempt;
                highScore = attemptScore;
            }
        }

        return highestTestAttempt;
    }
}
