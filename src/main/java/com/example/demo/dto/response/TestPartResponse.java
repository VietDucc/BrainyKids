package com.example.demo.dto.response;

import com.example.demo.dto.request.QuestionRequest;
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

public class TestPartResponse {
    private Long id;
    private PartType type;
    private List<QuestionResponse> questions;
}
