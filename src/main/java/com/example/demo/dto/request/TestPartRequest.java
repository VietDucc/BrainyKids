package com.example.demo.dto.request;

import com.example.demo.enums.PartType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestPartRequest {
    private Long id;
    private PartType type;
    private List<QuestionRequest> questions;
}
