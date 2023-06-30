package com.project1CATNIP.CATNIP.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Teacher teaches subjects.
 */

@Entity
@Getter @Setter
public class Teacher extends Person {

    @Id
    @GeneratedValue
    private Long teacherId;

}
