package com.example.demo.service;

import com.example.demo.entity.Deck;
import com.example.demo.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {
    @Autowired private DeckRepository deckRepository;

    public List<Deck> getAllDecks() {
        return deckRepository.findAll();
    }
    public Deck getDeck(Long id) {
        return deckRepository.findById(id).orElseThrow();
    }
    public Deck saveDeck(Deck deck) {
        return deckRepository.save(deck);
    }
    public void deleteDeck(Long id) {
        deckRepository.deleteById(id);
    }
}
