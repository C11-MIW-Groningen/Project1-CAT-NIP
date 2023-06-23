package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.List;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Courses to be followed at Make IT Work
 */

@Entity
@Getter @Setter
public class Program {

    @Id
    @GeneratedValue
    private Long programId;

    @Column (nullable = false)
    private String nameProgram;

    @OneToMany (mappedBy = "program")
    private List<Cohort> cohorts;

    @OneToMany (mappedBy = "program")
    private List<Course> courses;


    public int getNumberOfCohorts() {
        return cohorts.size();
    }

}
