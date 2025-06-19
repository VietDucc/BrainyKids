package com.example.demo.entity;

import com.example.demo.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "part_id", nullable = false)
    @JsonBackReference
    private Part part;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private String question;
    private String description;

    @Column(name = "img_src")
    private String imgSrc;

    @Column(name = "question_order")
    private int questionOrder;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<QuestionOption> questionOptions = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuestionProgress> questionProgress = new ArrayList<>();

    public Question(QuestionType type, String question, String description, int questionOrder) {
        this.type = type;
        this.question = question;
        this.description = description;
        this.questionOrder = questionOrder;
        this.questionOptions = new ArrayList<>();
        this.questionProgress = new ArrayList<>();
    }
}