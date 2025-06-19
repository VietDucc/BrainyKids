package com.example.demo.service;

import com.example.demo.entity.FlashCard;
import com.example.demo.repository.FlashCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlashCardService {
    @Autowired private FlashCardRepository cardRepository;

    public List<FlashCard> getAllCards() {
        return cardRepository.findAll();
    }
    public List<FlashCard> getAllCardsByDeckId(Long id) {
        return cardRepository.findByDeckId(id);
    }
    public FlashCard getCard(Long id) {
        return cardRepository.findById(id).orElse(null);
    }
    public FlashCard saveCard(FlashCard card) {
        return cardRepository.save(card);
    }
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
