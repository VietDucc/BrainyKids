package com.example.demo.dto.request;

import com.example.demo.entity.Answer;
import com.example.demo.entity.TestPart;
import com.example.demo.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionRequest {
    private Long id;
    private QuestionType type;
    private String question;
    private String questionImg;
    private String description;
    private Long questionOrder;
    private TestPart part;
    private Answer answer;
}
