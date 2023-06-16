package com.project1CATNIP.CATNIP.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * A student can enroll to a cohort of a program.
 */

@Entity
@Getter @Setter
public class Student extends Person {
    @Id @GeneratedValue
    private Long studentId;

    @Column (nullable = false)
    private String emailAddressStudent;
    @ManyToOne
    private Cohort cohort;

    public String getDisplayStudent() {
        String displayStudent = firstName;

        if (!infixName.equals("")) {
            displayStudent += " " + infixName;
        }

        displayStudent += " "+ lastName;
        return displayStudent;
    }
}
