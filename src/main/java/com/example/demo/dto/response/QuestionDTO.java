package com.example.demo.dto.response;

import com.example.demo.enums.QuestionType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    private Long id;
    private QuestionType type;
    private String question;
    private String description;
    private String imgSrc;
    private int questionOrder;
    private List<QuestionOptionDTO> questionOptions;
}