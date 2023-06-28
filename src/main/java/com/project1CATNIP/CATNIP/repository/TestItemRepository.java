package com.project1CATNIP.CATNIP.repository;


import com.project1CATNIP.CATNIP.model.Test;
import com.project1CATNIP.CATNIP.model.TestItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestItemRepository extends JpaRepository<TestItem, Long> {
    List<TestItem> findTestItemsByTest (Test test);
}
