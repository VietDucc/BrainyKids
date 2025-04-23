package com.example.demo.controller;

import com.example.demo.dto.request.UserFlashCardRequest;
import com.example.demo.entity.UserFlashcard;
import com.example.demo.service.UserFlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{clerkUserId}/cards")
public class UserFlashCardController {
    @Autowired private UserFlashcardService userFlashcardService;

    public UserFlashCardController(UserFlashcardService userFlashcardService) {
        this.userFlashcardService = userFlashcardService;
    }

    @GetMapping
    public List<UserFlashcard> getAllFlashCards(@PathVariable("clerkUserId") String clerkUserId) {
        return userFlashcardService.getAllUserFlashcards();
    }

    @GetMapping("/deck/{deckId}")
    public List<UserFlashcard> getCardsByDeckId(@PathVariable Long deckId, String clerkUserId) {
        return userFlashcardService.getUserFlashcardsByDeckId(deckId, clerkUserId);
    }

    @GetMapping("/{id}")
    public UserFlashcard getUserFlashcardById(@PathVariable Long id) {
        return userFlashcardService.getUserFlashcardById(id);
    }

    @PostMapping
    public UserFlashcard createUserFlashcard(@PathVariable String clerkUserId, @RequestBody UserFlashCardRequest request) {
        return userFlashcardService.createCardInDeck(request, clerkUserId);
    }

    @PutMapping("/{id}")
    public UserFlashcard updateCard(@PathVariable Long id, @RequestBody UserFlashCardRequest flashcardDTO, @PathVariable("clerkUserId") String clerkUserId) {
        return userFlashcardService.updateUserFlashcard(id, flashcardDTO, clerkUserId);
    }

    @DeleteMapping("/{id}")
    public void deleteUserFlashcard(@PathVariable Long id) {
        userFlashcardService.deleteCard(id);
    }
}
