package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Each course has one test (for now): portfolio or exam.
 * A student can take the test, which wil return in a test result.
 */

@Entity
@Getter @Setter
public class Test {
    @Id @GeneratedValue
    private Long testId;
    private String testName;

    @OneToOne
    private Course course;
}
