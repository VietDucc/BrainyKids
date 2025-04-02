package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clerkUserId;
    private int streakCount;
    private LocalDate lastCompletedDate;

    public UserProgress(String clerkUserId, int streakCount, LocalDate lastCompleteDate) {
        this.clerkUserId = clerkUserId;
        this.streakCount = streakCount;
        this.lastCompletedDate = lastCompletedDate;
    }
}
