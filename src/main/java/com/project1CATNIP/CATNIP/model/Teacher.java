package com.project1CATNIP.CATNIP.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl, Marcel Tubben <mhg.tubben@st.hanze.nl>>
 * Teacher teaches subjects.
 */

@Entity
@Getter @Setter
public class Teacher {
    @Id @GeneratedValue
    private Long teacherId;

    @Column (nullable = false)
    private String firstName;
    private String infixName;
    @Column (nullable = false)
    private String lastName;
    @Column (nullable = false)
    private String emailAddress;
    private String image;

    public String getDisplayTeacher() {
        StringBuilder fullname = new StringBuilder();
        fullname.append(firstName);
        if (!infixName.isEmpty()) {
            fullname.append(" ").append(infixName);
        }
        fullname.append(" ").append(lastName);

        return fullname.toString();
    }
}
