package com.project1CATNIP.CATNIP.repository;

import com.project1CATNIP.CATNIP.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
