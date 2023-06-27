package com.project1CATNIP.CATNIP.model;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Each test has one or more test attempts, taken by a student, and will yield a test result
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    private double attemptResult;

    @ManyToOne
    private Test test;

    @ManyToOne
    private Student student;

    //Todo: wordt gebruikt om attemptResult vast te stellen. Als TestItems klaar is, dan kan deze worden gebruikt.
    public void calculateAttemptResult(List<Integer> testItemScores) {
        int sumPoints = 0;
        for (Integer testItemScore : testItemScores) {
            sumPoints += testItemScore;
        }

        List<TestItem> testItems = this.test.getTestItems();
        int testMaxScore = testItems.stream().mapToInt(TestItem::getMaxPoints).sum();

        this.attemptResult = sumPoints/ (double) testMaxScore;
    }

}
