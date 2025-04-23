package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.TimeZone;

@Entity
@Table(name = "Userflash_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFlashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String front;
    private String back;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    @JsonBackReference
    private UserDeck deck;
}
