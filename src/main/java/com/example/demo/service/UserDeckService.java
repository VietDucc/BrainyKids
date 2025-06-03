package com.example.demo.service;

import com.example.demo.dto.response.UserDeckResponse;
import com.example.demo.entity.Deck;
import com.example.demo.entity.UserDeck;
import com.example.demo.repository.UserDeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDeckService {
    @Autowired private UserDeckRepository userDeckRepository;

    public UserDeck getDeckByIdAndClerkUserId(Long id, String clerkUserId) {
        return userDeckRepository.findByIdAndClerkUserId(id, clerkUserId)
                .orElseThrow(() -> new RuntimeException("Deck not found or does not belong to user"));
    }

    public UserDeckService(UserDeckRepository userDeckRepository) {
        this.userDeckRepository = userDeckRepository;
    }
    public List<UserDeckResponse> getAllDecksByUserId(String clerkUserId) {
        return userDeckRepository.findAllByClerkUserId(clerkUserId).stream().map(deck -> new UserDeckResponse(deck.getId(), deck.getName(), deck.getClerkUserId())).toList();
    }
    public UserDeck createDeck(String clerkUserId, UserDeck userDeck) {
        userDeck.setClerkUserId(clerkUserId);
        return userDeckRepository.save(userDeck);
    }
    public UserDeck updateDeck(String clerkUserId, UserDeck updatedDeck) {
        Long id = updatedDeck.getId();
        return userDeckRepository.findById(id).map(existingDeck -> {
            if (!existingDeck.getClerkUserId().equals(clerkUserId)) {
                throw new IllegalArgumentException("clerk user id does not match");
            }
            existingDeck.setName(updatedDeck.getName());
            return userDeckRepository.save(existingDeck);
        }).orElseThrow(() -> new IllegalArgumentException("Deck not found with id: " + id));
    }
    public UserDeck getUserDeckById(Long id) {
        return userDeckRepository.findUserDeckById(id);
    }
    public void deleteDeck(Long id) {
        userDeckRepository.deleteById(id);
    }
}
