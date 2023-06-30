package com.project1CATNIP.CATNIP.controller;

import com.project1CATNIP.CATNIP.model.TestItem;
import com.project1CATNIP.CATNIP.repository.TestItemRepository;
import com.project1CATNIP.CATNIP.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Handles all test-item interactions.
 */

@Controller
@RequiredArgsConstructor
public class TestItemController {

    private final TestItemRepository testItemRepository;

    @GetMapping("/testitem/delete/{itemId}")
    private String deleteTestItem(@PathVariable("itemId") Long itemId) {
        Optional<TestItem> itemToDelete = testItemRepository.findById(itemId);

        if (itemToDelete.isPresent()) {
            testItemRepository.delete(itemToDelete.get());
            return "redirect:/test/" + itemToDelete.get().getTest().getTestId() + "/addtestitems";
        }

        return "redirect:/course/all";
    }
}
