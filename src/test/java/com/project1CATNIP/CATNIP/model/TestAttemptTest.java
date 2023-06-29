package com.project1CATNIP.CATNIP.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestAttemptTest {

    com.project1CATNIP.CATNIP.model.Test test;
    List<TestItem> testItems;
    List<Integer> testItemScores;
    TestAttempt testAttempt;

    void setItems() {
        test = new com.project1CATNIP.CATNIP.model.Test();
        testItems = getTestItemList();
        testAttempt = new TestAttempt();
    }
//    @Test
//    void calculateAttemptResult() {
//        // Het blijkt onhandig een klasse de naam 'Test' te geven...
//        // ...vanwege de gelijknamige klasse in Jupiter; hier het volledige pad naar het model.
//
//        //Arrange
//        testItemScores = Arrays.asList(5, 10, 15);
//        test.setTestItems(testItems);
//        testAttempt.setTest(test);
//        double expectedAttemptResult = 0.5;  // sumPoints = 5 + 10 + 15 = 30, testMaxScore = 10 + 20 + 30 = 60
//
//        //Activate
//        testAttempt.calculateAttemptResult(testItemScores);
//
//        //Assert
//        assertEquals(expectedAttemptResult, testAttempt.getAttemptResult());
//    }

    private List<TestItem> getTestItemList() {
        TestItem testItem1 = new TestItem();
        testItem1.setMaxPoints(10);
        TestItem testItem2 = new TestItem();
        testItem2.setMaxPoints(20);
        TestItem testItem3 = new TestItem();
        testItem3.setMaxPoints(30);

        return Arrays.asList(testItem1, testItem2, testItem3);
    }
}