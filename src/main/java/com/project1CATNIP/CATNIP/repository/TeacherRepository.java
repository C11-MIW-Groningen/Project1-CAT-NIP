package com.project1CATNIP.CATNIP.repository;

import com.project1CATNIP.CATNIP.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository <Teacher, Long>{
    Optional<Teacher> findTeacherByEmailAddress(String email);
}
