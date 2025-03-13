package com.example.demo.entity;

import com.example.demo.enums.CorrectType;
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

    private CorrectType correctType;

    private String imageSrc;

    private String audioSrc;
    public ChallengeOption(Challenge challenge, String textOption, CorrectType correctType, String imageSrc, String audioSrc){
        this.challenge = challenge;
        this.textOption = textOption;
        this.correctType = correctType;
        this.imageSrc = imageSrc;
        this.audioSrc = audioSrc;
    }
}
