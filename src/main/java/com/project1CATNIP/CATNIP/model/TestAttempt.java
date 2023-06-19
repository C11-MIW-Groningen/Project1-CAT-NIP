package com.project1CATNIP.CATNIP.model;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Each test has one or more test attempts, taken by a student, and will yield a test result
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
public class TestAttempt {

    @Id
    @GeneratedValue
    private Long testAttemptId;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate attemptDate;

    @Column(nullable = false)
    private double attemptResult;

    @ManyToOne
    private Test test;

    @OneToOne
    private Student student;

    public void calculateAttemptResult(List<Integer> testItemScores) {
        int sumPoints = 0;
        for (Integer testItemScore : testItemScores) {
            sumPoints += testItemScore;
        }

        List<TestItem> testItems = test.getTestItem();
        int testMaxScore = testItems.stream().mapToInt(TestItem::getMaxPoints).sum();

        attemptResult = sumPoints/ (double) testMaxScore;
    }
}
