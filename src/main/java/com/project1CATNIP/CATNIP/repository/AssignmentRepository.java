package com.project1CATNIP.CATNIP.repository;

import com.project1CATNIP.CATNIP.model.Assignment;
import com.project1CATNIP.CATNIP.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByCourse(Course course);
}
