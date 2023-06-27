package com.project1CATNIP.CATNIP.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>
 * Doel van je programma.
 */
class ProgramTest {

    @Test
    void setNameProgramWithValidName() {
        String validName = "Software Engineering";
        Program program2 = new Program();

        program2.setNameProgram(validName);

        assertEquals("Software Engineering", program2.getNameProgram());
    }
    @Test
    void setNameProgramWithInvalidName() {
        String invalidName = "a";
        Program program2 = new Program();

        assertThrows(IllegalArgumentException.class, () -> {program2.setNameProgram(invalidName);});
    }

    @Test
    void setNameProgramWithNoInput() {
        Program program3 = new Program();

        assertThrows(IllegalArgumentException.class, () -> {program3.setNameProgram(null);});
    }

    @Test
    void setNameProgramWithTooManySpaces() {
        String invalidName2 = "       ";
        Program program4 = new Program();

        assertThrows(IllegalArgumentException.class, () -> {program4.setNameProgram(invalidName2);});
    }
}