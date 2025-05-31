package com.example.demo.dto.request;

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
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String choiceAImg;
    private String choiceBImg;
    private String choiceCImg;
    private String choiceDImg;
    private String correctAnswer;
}
