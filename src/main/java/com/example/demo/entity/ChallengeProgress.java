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

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user; // Giả sử có entity User

    @ManyToOne
    @JoinColumn(name="challenge_id", nullable=false)
    @JsonBackReference
    private Challenge challenge;

    private boolean completed;
}
