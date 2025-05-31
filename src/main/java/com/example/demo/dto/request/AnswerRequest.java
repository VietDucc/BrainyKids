package com.example.demo.dto.request;

import com.example.demo.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerRequest {
    private Long id;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String choiceAImg;
    private String choiceBImg;
    private String choiceCImg;
    private String choiceDImg;
    private String correctAnswer;
    private Question question;
}
