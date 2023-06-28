package com.project1CATNIP.CATNIP.dto;

import com.project1CATNIP.CATNIP.model.Student;
import com.project1CATNIP.CATNIP.model.Test;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestAttemptDTO {

    private Test test;
    private Student student;
}
