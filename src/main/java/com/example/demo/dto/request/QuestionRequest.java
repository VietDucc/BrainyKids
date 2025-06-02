package com.example.demo.dto.request;

import com.example.demo.enums.QuestionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class QuestionRequest {
    private QuestionType type;
    private String question;
    private String description;
    private String imgSrc;
    private int questionOrder;
    private long partId;
    private List<QuestionOptionRequestDto> questionOptions = new ArrayList<>();
}