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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"assignment_name", "assignment_number", "day_part"})})
public class Assignment {

    @Id
    @GeneratedValue
    private Long assignmentId;

    @Column (nullable = false, name = "assignment_name")
    private String assignmentName;

    @Column (name = "day_part")
    private Integer dayPart;

    @Column (name = "assignment_number")
    private Integer assignmentNumber;

    @ManyToOne
    private Course course;

    public void setAssignmentName(String assignmentName) {
        if (assignmentName == null || assignmentName.trim().length() < 2) {
            throw new IllegalArgumentException("Assignment name must be more than one character");
        }
        this.assignmentName = assignmentName;
    }

    public String getDisplayAssignment() {
        return String.format("%d.%d %s", dayPart, assignmentNumber, assignmentName);
    }
}
