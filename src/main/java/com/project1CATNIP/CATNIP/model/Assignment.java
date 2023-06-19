package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Assignments are part of courses. A student makes them, but they don't get grades.
 */

@Entity
@Getter @Setter
public class Assignment {

<<<<<<< Updated upstream
    @Id
    @GeneratedValue
=======
    @Id @GeneratedValue
>>>>>>> Stashed changes
    private Long assignmentId;

    @Column (nullable = false)
    private String assignmentName;

    private Integer dayPart;

    private Integer assignmentNumber;

    @ManyToOne
    private Course course;

    public String getDisplayAssignment() {
        return String.format("%d.%d %s", dayPart, assignmentNumber, assignmentName);
    }
}
