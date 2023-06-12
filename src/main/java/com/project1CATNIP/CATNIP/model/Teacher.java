package com.project1CATNIP.CATNIP.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl, Marcel Tubben <mhg.tubben@st.hanze.nl>>
 * Teacher teaches subjects.
 */

public class Teacher {
    @Id @GeneratedValue
    private Long teacherId;
}
