package com.project1CATNIP.CATNIP.repository;

import com.project1CATNIP.CATNIP.model.Student;
import com.project1CATNIP.CATNIP.model.Test;
import com.project1CATNIP.CATNIP.model.TestAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestAttemptRepository extends JpaRepository<TestAttempt, Long> {

    List<TestAttempt> findTestAttemptsByStudentAndTestIn(Student studentId, List<Test> testId);
    List<TestAttempt> findTestAttemptsByStudentInAndTestIn(List<Student> studentId, List<Test> testId);
}
