package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @Column (nullable = false)
    private String emailAddressStudent;

    @ManyToOne
    private Cohort cohort;
}
