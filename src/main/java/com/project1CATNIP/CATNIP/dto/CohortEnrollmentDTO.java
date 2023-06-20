package com.project1CATNIP.CATNIP.dto;

import com.project1CATNIP.CATNIP.model.Cohort;
import com.project1CATNIP.CATNIP.model.Student;
import lombok.Builder;
import lombok.Data;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Capture the enrolling of a student to a cohort
 */

@Data
@Builder
public class CohortEnrollmentDTO {

    private Cohort cohort;
    private Student student;
}
