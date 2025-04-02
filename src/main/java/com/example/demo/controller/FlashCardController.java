package com.example.demo.controller;

import com.example.demo.dto.request.CreateFlashCardRequest;
import com.example.demo.entity.Deck;
import com.example.demo.entity.FlashCard;
import com.example.demo.service.FlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/flashcards")
public class FlashCardController {

    @Autowired
    private FlashCardService flashCardService;

    @GetMapping
    public Object getFlashCards() {
        List<FlashCard> flashCards = flashCardService.getAllFlashCards();
        List<Deck> decks = flashCardService.getAllDecks();

        return new Object() {
            public final List<FlashCardDTO> cards = flashCards.stream().map(card -> new FlashCardDTO(
                    card.getId(),
                    card.getFront(),
                    card.getBack(),
                    card.getDeck().getId()
            )).collect(Collectors.toList());

            public final List<DeckDTO> decksList = decks.stream().map(deck -> new DeckDTO(
                    deck.getId(),
                    deck.getName()
            )).collect(Collectors.toList());
        };
    }

    @PostMapping
    public FlashCardDTO createFlashCard(@RequestBody CreateFlashCardRequest request) {
        FlashCard newCard = flashCardService.createFlashCard(request.getDeckId(), request.getFront(), request.getBack());
        return new FlashCardDTO(newCard.getId(), newCard.getFront(), newCard.getBack(), newCard.getDeck().getId());
    }

    @Getter
    public static class FlashCardDTO {
        private final Long id;
        private final String front;
        private final String back;
        private final Long deckId;

        public FlashCardDTO(Long id, String front, String back, Long deckId) {
            this.id = id;
            this.front = front;
            this.back = back;
            this.deckId = deckId;
        }
    }

    @Getter
    public static class DeckDTO {
        private final Long id;
        private final String name;

        public DeckDTO(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Getter
    public static class CreateFlashCardRequest {
        private Long deckId;
        private String front;
        private String back;
    }
}
