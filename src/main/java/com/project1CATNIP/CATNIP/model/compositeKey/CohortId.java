package com.project1CATNIP.CATNIP.model.compositeKey;/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.Course;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
@Getter @Setter
public class CohortId implements Serializable {

    private Integer number;
    private Course course;
}
