package com.project1CATNIP.CATNIP.model;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>
 * Testing for Cohort methods.
 */

public class CohortTest {
    @ParameterizedTest (name = "{0}")
    @DisplayName("Set valid cohort number")
    @ValueSource(ints = {1, 10, 50, 100})
    void setValidCohortNumber(int validNumbers) {
        Cohort cohort = new Cohort();

        assertDoesNotThrow(() -> cohort.setNumber(validNumbers));
    }

    @ParameterizedTest (name = "{0}")
    @DisplayName("Set invalid cohort number")
    @ValueSource(ints = {Integer.MIN_VALUE, -1, 0, 101, Integer.MAX_VALUE})
    void setInvalidCohortNumber(int invalidNumbers) {
        Cohort cohort = new Cohort();

        assertThrows(IllegalArgumentException.class, () -> cohort.setNumber(invalidNumbers));
    }
}
