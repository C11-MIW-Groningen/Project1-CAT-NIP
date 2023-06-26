package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Person is an abstract superclass with attributes for the name.
 */

@Getter @Setter
@MappedSuperclass
public abstract class Person {

    @Column(nullable = false)
    String firstName;

    String infixName = "";

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    @Email(message = "E-mail must be properly formatted")
    private String emailAddress;

    public String getDisplayName() {
        String displayName = firstName;

        if (infixName != null && !infixName.equals("")) {
            displayName += " " + infixName;
        }

        displayName += " "+ lastName;
        return displayName;
    }



}
