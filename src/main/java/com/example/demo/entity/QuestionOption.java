package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "question_option")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference
    private Question question;

    private String answers;
    private boolean correct;

    @Column(name = "img_src")
    private String imgSrc;

    private String audioSrc;

    public QuestionOption(Question question, String answers, boolean correct, String imgSrc, String audioSrc) {
        this.question = question;
        this.answers = answers;
        this.correct = correct;
        this.imgSrc = imgSrc;
        this.audioSrc = audioSrc;
    }
}
