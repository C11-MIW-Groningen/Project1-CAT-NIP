package com.project1CATNIP.CATNIP.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Each course can have multiple tests: portfolio or exam.
 */

@Entity
@Getter @Setter
public class Test {

    @Id
    @GeneratedValue
    private Long testId;

    @Column (nullable = false)
    private String nameTest;

    @ManyToOne
    private Course course;

    @OneToMany (mappedBy = "test")
    private List<TestAttempt> testAttempts;

    @OneToMany
    private List<TestItem> testItem = new ArrayList<>();

    @OneToMany (mappedBy = "test", cascade = CascadeType.PERSIST)
    @Builder.Default private List<TestItem> testItems = new ArrayList<>();

    public void setNameTest(String nameTest) {
        if (nameTest == null || nameTest.trim().length() < 2) {
            throw new IllegalArgumentException("Test must have a name (with more than 1 character)");
        }
        this.nameTest = nameTest;
    }

    public void addTestItem(TestItem testItem) {
        testItems.add(testItem);
    }

    public void removeTestItem(TestItem testItem) {
        testItems.remove(testItem);
    }

    public String getDisplayName() {
        return course.getCourseName() + ": " + nameTest;
    }

}
