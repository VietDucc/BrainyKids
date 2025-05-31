package com.example.demo.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestSubmissionRequest {
    private Long testId;
    private List<UserAnswerDTO> answers;

    @Getter
    @Setter
    public static class UserAnswerDTO {
        private Long questionId;
        private String selectedAnswer;
    }
}
