package com.project1CATNIP.CATNIP.model;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Each test has one or more test attempts, taken by a student, and yield(s) (a) test result(s)
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

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

    public void setAttemptDate(LocalDate attemptDate) {
        if (attemptDate == null || attemptDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Test attempt must have a date that is not in the future.");
        }
        this.attemptDate = attemptDate;
    }

    public void setAttemptResult(Double attemptResult) {
        if (attemptResult == null || attemptResult < 1 || attemptResult > 10) {
            throw new IllegalArgumentException("Item number must be a positive integer");
        }
        this.attemptResult = attemptResult;
    }

}
