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

    @Column (nullable = false)
    String firstName;

    String infixName;

    @Column (nullable = false)
    String lastName;

}
