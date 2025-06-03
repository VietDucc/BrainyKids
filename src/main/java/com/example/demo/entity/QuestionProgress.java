package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "question_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference
    private Question question;

    private boolean completed;

    public QuestionProgress(String userId, Question question, boolean completed) {
        this.userId = userId;
        this.question = question;
        this.completed = completed;
    }
}