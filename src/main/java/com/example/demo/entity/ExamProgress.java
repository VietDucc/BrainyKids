package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exam_progress")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Column(name = "completed_questions")
    private Integer completedQuestions;

    @Column(name = "total_questions")
    private Integer totalQuestions;

    @Column(name = "score")
    private Double score;

    @Column(name = "completed")
    private boolean completed;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}