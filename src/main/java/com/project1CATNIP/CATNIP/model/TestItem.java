package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Testitems are smaller objects, part of a test
 */

@Entity
@Getter @Setter
public class TestItem implements Comparable<TestItem> {

    @Id
    @GeneratedValue
    private Long itemId;

    @Column(nullable = false)
    private Integer itemNumberForTest;

    private String description;

    @Column(nullable = false)
    private Integer maxPoints;

    @ManyToOne
    private Test test;

    public void setItemNumberForTest(Integer itemNumberForTest) {
        if (itemNumberForTest == null) {
            throw new IllegalArgumentException("Item number must be a positive integer");
        }
        this.itemNumberForTest = itemNumberForTest;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().length() < 2) {
            throw new IllegalArgumentException("Item must have a description (with more than 1 character)");
        }
        this.description = description;
    }

    public void setMaxPoints(Integer maxPoints) {
        if (maxPoints == null) {
            throw new IllegalArgumentException("Item number must be a positive integer");
        }
        this.maxPoints = maxPoints;
    }

    @Override
    public int compareTo(TestItem testItem) {
        return itemNumberForTest.compareTo(testItem.itemNumberForTest);
    }
}
