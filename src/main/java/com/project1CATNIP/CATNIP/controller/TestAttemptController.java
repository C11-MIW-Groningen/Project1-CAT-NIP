package com.project1CATNIP.CATNIP.controller;

/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.TestAttempt;
import com.project1CATNIP.CATNIP.repository.TestAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestAttemptController {

    private final TestAttemptRepository testAttemptRepository;

    private List<TestAttempt> findAllTestAttemptsByStudent(Long studentId) {
        return testAttemptRepository.findTestAttemptsByStudent(studentId);
    }
}
