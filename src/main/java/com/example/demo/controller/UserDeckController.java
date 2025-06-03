package com.example.demo.controller;

import com.example.demo.dto.response.UserDeckResponse;
import com.example.demo.entity.UserDeck;
import com.example.demo.repository.UserDeckRepository;
import com.example.demo.service.UserDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{clerkUserId}/decks")
public class UserDeckController {
    @Autowired private UserDeckService userDeckService;
    @Autowired private UserDeckRepository userDeckRepository;

    public UserDeckController(UserDeckService userDeckService) {
        this.userDeckService = userDeckService;
    }

    @GetMapping("/{id}")
    public UserDeck getUserDeckById(@PathVariable String clerkUserId, @PathVariable Long id) {
        return userDeckService.getDeckByIdAndClerkUserId(id, clerkUserId);
    }
    @GetMapping
    public List<UserDeckResponse> getUserDecks(@PathVariable String clerkUserId) {
        return userDeckService.getAllDecksByUserId(clerkUserId);
    }

    @PostMapping
    public UserDeck createUserDeck(@PathVariable String clerkUserId ,@RequestBody UserDeck userDeck) {
        return userDeckService.createDeck(clerkUserId, userDeck);
    }

    @PutMapping
    public UserDeck updateUserDeck(@PathVariable String clerkUserId, @RequestBody UserDeck userDeck) {
        return userDeckService.updateDeck(clerkUserId, userDeck);
    }

    @DeleteMapping("/{id}")
    public void deleteUserDeck(@PathVariable Long id) {
        userDeckService.deleteDeck(id);
    }
}
