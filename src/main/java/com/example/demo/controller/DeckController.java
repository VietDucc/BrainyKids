package com.example.demo.controller;

import com.example.demo.entity.Deck;
import com.example.demo.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.DeckService;

import java.util.List;

@RestController
@RequestMapping("api/decks")
public class DeckController {

    // gồm các phương thức POST, GET, PUT, DELETE cho decks

    @Autowired private DeckService deckService;
    @Autowired private DeckRepository deckRepository;

    @GetMapping
    public List<Deck> getAllDecks() {
        return deckService.getAllDecks();
    }

    @GetMapping("/{id}")
    public Deck getDeckById(@PathVariable Long id) {
        return deckService.getDeck(id);
    }

    @PostMapping
    public Deck createDeck(@RequestBody Deck deck) {
        return deckService.saveDeck(deck);
    }

    @PutMapping("/{id}")
    public Deck updateDeck(@PathVariable Long id, @RequestBody Deck updated) {
        Deck existingDeck = deckService.getDeck(id);
        existingDeck.setName(updated.getName());

        return deckService.saveDeck(existingDeck);
    }

    @DeleteMapping("/{id}")
    public void deleteDeck(@PathVariable Long id) {
        deckService.deleteDeck(id);
    }
}
