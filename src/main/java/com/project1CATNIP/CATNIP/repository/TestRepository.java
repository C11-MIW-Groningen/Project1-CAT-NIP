package com.project1CATNIP.CATNIP.repository;

import com.project1CATNIP.CATNIP.model.Course;
import com.project1CATNIP.CATNIP.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByCourse(Course courseId);
}
