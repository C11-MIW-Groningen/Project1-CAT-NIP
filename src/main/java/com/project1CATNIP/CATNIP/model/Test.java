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

    @OneToMany (mappedBy = "test", cascade = CascadeType.PERSIST)
    @Builder.Default private List<TestItem> testItems = new ArrayList<>();

    public void addTestItem(TestItem testItem) {
        testItems.add(testItem);
    }

    public void removeTestItem(TestItem testItem) {
        testItems.remove(testItem);
    }
}
