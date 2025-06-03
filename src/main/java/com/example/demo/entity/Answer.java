package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;
}
