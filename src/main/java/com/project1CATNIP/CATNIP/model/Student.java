package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * A student can enroll to a cohort of a program.
 */

@Entity
@Getter @Setter
public class Student {
    @Id @GeneratedValue
    private Long studentId;

    @Column(nullable = false)
    private String firstNameStudent;
    private String infixNameStudent;
    @Column (nullable = false)
    private String lastNameStudent;
    @Column (nullable = false)
    private String emailAddressStudent;
    @ManyToOne
    private Cohort cohort;

    public String getDisplayStudent() {
        String displayStudent = firstNameStudent;

        if (!infixNameStudent.equals("")) {
            displayStudent += " " + infixNameStudent;
        }

        displayStudent += " "+ lastNameStudent;
        return displayStudent;
    }
}
