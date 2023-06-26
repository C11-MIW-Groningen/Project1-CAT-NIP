package com.project1CATNIP.CATNIP.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>
 * Doel van je programma.
 */
class ProgramTest {

    @Test
    void setNameProgramWithValidName() throws Program.InvalidProgramNameException {
        String validName = "Software Engineering";
        Program program2 = new Program();

        program2.setNameProgram(validName);

        assertEquals("Software Engineering", program2.getNameProgram());
    }
    @Test
    void setNameProgramWithInvalidName() {
        String invalidName = "a";
        Program program2 = new Program();

        assertThrows(Program.InvalidProgramNameException.class, () -> {program2.setNameProgram(invalidName);});
    }
}