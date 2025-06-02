package com.example.demo.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamProgressDTO {
    private Long id;
    private String userId;
    private Long examId;
    private String examName;
    private Integer completedQuestions;
    private Integer totalQuestions;
    private Double score;
    private boolean completed;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Double progressPercentage;
}
