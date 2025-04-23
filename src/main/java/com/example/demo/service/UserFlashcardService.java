package com.example.demo.service;

import com.example.demo.dto.request.UserFlashCardRequest;
import com.example.demo.entity.UserDeck;
import com.example.demo.entity.UserFlashcard;
import com.example.demo.repository.UserDeckRepository;
import com.example.demo.repository.UserFlashCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFlashcardService {
    @Autowired private UserFlashCardRepository userFlashCardRepository;
    @Autowired private UserDeckRepository userDeckRepository;

    public UserFlashcardService(UserFlashCardRepository userFlashCardRepository, UserDeckRepository userDeckRepository) {
        this.userFlashCardRepository = userFlashCardRepository;
        this.userDeckRepository = userDeckRepository;
    }
    public List<UserFlashcard> getAllUserFlashcards() {
        return userFlashCardRepository.findAll();
    }
    public List<UserFlashcard> getCardsByDeckId(String clerkUserId, Long deckId) {
        return userFlashCardRepository.findByDeck_IdAndDeck_ClerkUserId(deckId, clerkUserId);
    }
    public UserFlashcard getUserFlashcardById(Long id) {
        return userFlashCardRepository.findById(id).orElse(null);
    }
    public List<UserFlashcard> getUserFlashcardsByDeckId(Long deckId, String clerkUserId) {
        return userFlashCardRepository.findByDeck_IdAndDeck_ClerkUserId(deckId, clerkUserId);
    }
    public UserFlashcard getUserFlashcard(Long flashcardId) {
        return userFlashCardRepository.findById(flashcardId).orElse(null);
    }
    public UserFlashcard saveUserFlashcard(UserFlashcard userFlashcard) {
        return userFlashCardRepository.save(userFlashcard);
    }
    public void deleteCard(Long id) {
        userFlashCardRepository.deleteById(id);
    }
    public UserFlashcard createCardInDeck(UserFlashCardRequest request, String clerkUserId) {
        UserDeck deck = userDeckRepository.findById(request.getDeckId())
                .filter(d -> d.getClerkUserId().equals(clerkUserId))
                .orElseThrow(() -> new RuntimeException("Deck not found or access denied"));

        UserFlashcard newCard = UserFlashcard.builder()
                .front(request.getFront())
                .back(request.getBack())
                .deck(deck)
                .build();

        return userFlashCardRepository.save(newCard);
    }
    public UserFlashcard updateUserFlashcard(Long id, UserFlashCardRequest request, String clerkUserId) {
        UserFlashcard existingCard = userFlashCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (!existingCard.getDeck().getClerkUserId().equals(clerkUserId)) {
            throw new RuntimeException("Access denied");
        }

        existingCard.setFront(request.getFront());
        existingCard.setBack(request.getBack());

        return userFlashCardRepository.save(existingCard);
    }
}
