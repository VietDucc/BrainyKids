package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.TimeZone;

@Entity
@Table(name = "flash_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String front;

    private String back;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;
}
