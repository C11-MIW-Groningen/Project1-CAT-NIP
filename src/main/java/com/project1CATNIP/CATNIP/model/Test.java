package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Each course has one test (for now): portfolio or exam.
 */

@Entity
@Getter @Setter
public class Test {
    @Id @GeneratedValue
    private Long testId;
    @Column (nullable = false)
    private String nameTest;

    @OneToOne
    private Course course;
}