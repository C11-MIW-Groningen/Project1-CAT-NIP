package com.project1CATNIP.CATNIP.repository;

import com.project1CATNIP.CATNIP.model.Cohort;
import com.project1CATNIP.CATNIP.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CohortRepository extends JpaRepository<Cohort, Long> {
    List<Cohort> findByProgram(Program program);
}
