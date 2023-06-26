package com.project1CATNIP.CATNIP.model;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * A Cohort is an instance of a Course; it is defined by a number and a Course
 */

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"number", "program_program_id"})})
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Cohort {

    @Id
    @GeneratedValue
    Long cohortId;

    @Column (nullable = false)
    private Integer number;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ManyToOne
    private Program program;

    @OneToMany(mappedBy = "cohort", cascade = CascadeType.PERSIST)
    @Builder.Default private Set<Student> students = new HashSet<>();

    public String getDisplayCohort() {
        return String.format("%s %d", program.getNameProgram(), number);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public boolean isOldCohort() {
        return endDate.isBefore(LocalDate.now());
    }
}
