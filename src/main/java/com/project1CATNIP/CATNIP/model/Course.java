package com.project1CATNIP.CATNIP.model;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Courses to be followed as part of a program at Make IT Work
 */

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Course {

    @Id
    @GeneratedValue
    private Long courseId;

    @Column (nullable = false)
    private String courseName;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Program program;

    @OneToMany (mappedBy = "course")
    private List<Test> tests;

    @OneToMany (mappedBy = "course")
    private List<Assignment> assignments;
}
