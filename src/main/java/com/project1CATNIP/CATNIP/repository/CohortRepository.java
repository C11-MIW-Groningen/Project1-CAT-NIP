package com.project1CATNIP.CATNIP.repository;/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CohortRepository extends JpaRepository<Cohort, Long> {

}
