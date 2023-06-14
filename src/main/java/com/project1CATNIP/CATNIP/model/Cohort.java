package com.project1CATNIP.CATNIP.model;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl, Marcel Tubben <mhg.tubben@st.hanze.nl>>
 * A Cohort is an instance of a Course; it is defined by a number and a Course
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
public class Cohort {

    @Id @GeneratedValue
    Long cohortId;
    @Column (nullable = false)
    private Integer number;
    @ManyToOne
    private Program program;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @OneToMany(mappedBy = "cohort")
    private List<Student> students;

    public String getDisplayCohort() {
        return String.format("%s %d", program.getNameProgram(), number);
    }
}
