package com.project1CATNIP.CATNIP.model;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl, Marcel Tubben <mhg.tubben@st.hanze.nl>>
 * A Cohort is an instance of a Course; it is defined by a number and a Course
 */

import com.project1CATNIP.CATNIP.model.compositeKey.CohortId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Cohort {

    @EmbeddedId
    CohortId cohortId;

}
