package com.example.demo.controller;

import com.example.demo.entity.UserDeck;
import com.example.demo.repository.UserDeckRepository;
import com.example.demo.service.UserDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.DeckService;

import java.util.List;

@RestController
@RequestMapping("/api/{clerkUserId}/decks")
public class UserDeckController {
    @Autowired private UserDeckService userDeckService;
    @Autowired private UserDeckRepository userDeckRepository;

    public UserDeckController(UserDeckService userDeckService) {
        this.userDeckService = userDeckService;
    }

    @GetMapping
    public List<UserDeck> getUserDecks(@PathVariable String clerkUserId) {
        return userDeckService.getAllDecksByUserId(clerkUserId);
    }

    @PostMapping
    public UserDeck createUserDeck(@PathVariable String clerkUserId ,@RequestBody UserDeck userDeck) {
        return userDeckService.createDeck(clerkUserId, userDeck);
    }

    @DeleteMapping("/{id}")
    public void deleteUserDeck(@PathVariable Long id) {
        userDeckService.deleteDeck(id);
    }
}
