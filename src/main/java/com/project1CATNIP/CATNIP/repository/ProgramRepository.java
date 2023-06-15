package com.project1CATNIP.CATNIP.repository;

import com.project1CATNIP.CATNIP.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {

}
