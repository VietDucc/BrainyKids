package com.example.demo.controller;


import com.example.demo.entity.FlashCard;
import com.example.demo.service.FlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
public class FlashCardController {

    // gồm các phương thức POST, GET, PUT, DELETE cho flashcards

    @Autowired private FlashCardService flashCardService;

    @GetMapping
    public List<FlashCard> getAllFlashCards() {
        return flashCardService.getAllCards();
    }

    @GetMapping("/deck/{deckId}")
    public List<FlashCard> getCardsByDeckId(@PathVariable Long deckId) {
        return flashCardService.getAllCardsByDeckId(deckId);
    }

    @GetMapping("/{id}")
    public FlashCard getFlashCardById(@PathVariable Long id) {
        return flashCardService.getCard(id);
    }

    @PostMapping
    public FlashCard createFlashCard(@RequestBody FlashCard flashCard) {
        return flashCardService.saveCard(flashCard);
    }

    @PutMapping("/{id}")
    public FlashCard updateFlashCard(@PathVariable Long id, @RequestBody FlashCard flashCard) {
        FlashCard existing = flashCardService.getCard(id);
        existing.setFront(flashCard.getFront());
        existing.setBack(flashCard.getBack());
        return flashCardService.saveCard(existing);

    }

    @DeleteMapping("/{id}")
    public void deleteFlashCard(@PathVariable Long id) {
        flashCardService.deleteCard(id);
    }
}


