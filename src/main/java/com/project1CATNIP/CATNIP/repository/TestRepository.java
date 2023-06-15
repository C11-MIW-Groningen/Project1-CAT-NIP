package com.project1CATNIP.CATNIP.repository;

import com.project1CATNIP.CATNIP.model.Course;
import com.project1CATNIP.CATNIP.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findByCourse(Course courseId);
}
