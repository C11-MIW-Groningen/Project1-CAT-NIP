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

    @Override
    public int compareTo(TestItem testItem) {
        return itemNumberForTest.compareTo(testItem.itemNumberForTest);
    }
}
