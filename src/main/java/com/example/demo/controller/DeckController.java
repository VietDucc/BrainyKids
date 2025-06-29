package com.example.demo.controller;

import com.example.demo.entity.Deck;
import com.example.demo.repository.DeckRepository;
import com.example.demo.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Deck createDeck(@RequestBody Deck deck) {
        return deckService.saveDeck(deck);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Deck updateDeck(@PathVariable Long id, @RequestBody Deck updated) {
        Deck existingDeck = deckService.getDeck(id);
        existingDeck.setName(updated.getName());

        return deckService.saveDeck(existingDeck);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteDeck(@PathVariable Long id) {
        deckService.deleteDeck(id);
    }
}
