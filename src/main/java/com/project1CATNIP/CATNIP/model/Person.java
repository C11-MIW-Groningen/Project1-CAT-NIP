package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

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
    private String emailAddress;

    public String getDisplayName() {
        String displayName = firstName;

        if (infixName != null && !infixName.equals("")) {
            displayName += " " + infixName;
        }

        displayName += " "+ lastName;
        return displayName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().length() < 2) {
            throw new IllegalArgumentException("First name must be more than one character");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().length() < 2) {
            throw new IllegalArgumentException("Last name must be more than one character");
        }
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.trim().length() < 2) {
            throw new IllegalArgumentException("Email address must be more than one character");
        }
        this.emailAddress = emailAddress;
    }
}
