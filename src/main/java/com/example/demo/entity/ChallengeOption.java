package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "challenge_option")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="challenge_id", nullable=false)
    @JsonBackReference
    private Challenge challenge;
    private String textOption;

    private boolean correct;

    private String imageSrc;

    private String audioSrc;
    public ChallengeOption(Challenge challenge, String textOption,boolean correct , String imageSrc, String audioSrc){
        this.challenge = challenge;
        this.textOption = textOption;
        this.correct = correct;
        this.imageSrc = imageSrc;
        this.audioSrc = audioSrc;
    }
}
