package com.project1CATNIP.CATNIP.repository;

import com.project1CATNIP.CATNIP.model.Course;
import com.project1CATNIP.CATNIP.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByProgram(Program program);
}
