package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "challenge_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String userId;

    @ManyToOne
    @JoinColumn(name="challenge_id", nullable=false)
    @JsonBackReference
    private Challenge challenge;

    private boolean completed;
    public ChallengeProgress(String userId, Challenge challenge, boolean completed){
        this.userId = userId;
        this.challenge = challenge;
        this.completed = completed;
    }
}
