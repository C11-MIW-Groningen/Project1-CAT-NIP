package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl, Marcel Tubben <mhg.tubben@st.hanze.nl>>
 * Courses to be followed at Make IT Work
 */

@Entity
@Getter @Setter
public class Course {
    @Id @GeneratedValue
    private Long courseId;

    private String nameCourse;

}
