package com.project1CATNIP.CATNIP.dto;

import com.project1CATNIP.CATNIP.model.Test;
import com.project1CATNIP.CATNIP.model.TestItem;
import lombok.Builder;
import lombok.Data;

/**
 * Author: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Adding testitems to test.
 */

@Data
@Builder
public class TestItemsToTestDTO {

    private Test test;
    private TestItem testItem;
}
