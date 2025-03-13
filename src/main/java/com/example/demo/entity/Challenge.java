package com.example.demo.entity;

import com.example.demo.enums.ChallengeType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chanllenges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="lesson_id", nullable=false)
    @JsonBackReference
    private Lesson lesson;

    private ChallengeType type;

    private String question;

    @Column(name = "order_challenge")
    private int orderChallenge;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ChallengeOption> challengesOption = new ArrayList<>();

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ChallengeProgress> challengesProgress = new ArrayList<>();

    public Challenge(ChallengeType type, String question, int orderChallenge){
        this.type = type;
        this.question = question;
        this.orderChallenge = orderChallenge;
        this.challengesOption = new ArrayList<>();
        this.challengesProgress = new ArrayList<>();
    }
}
