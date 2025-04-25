package com.example.demo.repository;

import com.example.demo.entity.Deck;
import com.example.demo.entity.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    List<FlashCard> findByDeckId(Long deckID);
}

