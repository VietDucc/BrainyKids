package com.example.demo.service;

import com.example.demo.entity.Deck;
import com.example.demo.entity.FlashCard;
import com.example.demo.repository.FlashCardRepository;
import com.example.demo.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlashCardService {

    private final FlashCardRepository flashCardRepository;

    private final DeckRepository deckRepository;

    public FlashCard createFlashCard(Long deckId, String front, String back) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck not found"));
        FlashCard flashCard = FlashCard.builder()
                .front(front)
                .back(back)
                .deck(deck)
                .build();
        return flashCardRepository.save(flashCard);
    }

    public List<Deck> getAllDecks() {
        return deckRepository.findAll();
    }

    public List<FlashCard> getAllFlashCards() {
        return flashCardRepository.findAll();
    }
}
