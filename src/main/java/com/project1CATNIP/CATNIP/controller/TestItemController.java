package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.repository.TestItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.persistence.Entity;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handles all test-item interactions.
 */

@Controller
@RequiredArgsConstructor
public class TestItemController {

    private final TestItemRepository testItemRepository;



}
