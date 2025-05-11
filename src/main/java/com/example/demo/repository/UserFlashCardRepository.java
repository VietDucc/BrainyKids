package com.example.demo.repository;

import com.example.demo.entity.UserDeck;
import com.example.demo.entity.UserFlashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFlashCardRepository  extends JpaRepository<UserFlashcard, Long> {
    List<UserFlashcard> findByDeck_IdAndDeck_ClerkUserId(Long deckId, String clerkUserId);
}
