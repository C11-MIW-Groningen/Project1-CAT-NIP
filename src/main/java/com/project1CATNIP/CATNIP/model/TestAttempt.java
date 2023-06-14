package com.project1CATNIP.CATNIP.model;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Each test has one or more test attempts, taken by a student, and will yield a test result
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class TestAttempt {

    @Id @GeneratedValue
    private Long testAttemptId;
    @ManyToOne
    private Test test;
    @OneToOne
    private Student student;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate attemptDate;
    @Column(nullable = false)
    private double attemptResult;

}
