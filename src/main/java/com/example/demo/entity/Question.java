package com.example.demo.entity;

import com.example.demo.enums.QuestionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Column(columnDefinition = "TEXT")
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

    @ManyToOne
    @JoinColumn(name = "part_id")
    private TestPart part;
}
