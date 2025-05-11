package com.example.demo.service;

import com.example.demo.entity.UserDeck;
import com.example.demo.repository.UserDeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDeckService {
    @Autowired private UserDeckRepository userDeckRepository;

    public UserDeckService(UserDeckRepository userDeckRepository) {
        this.userDeckRepository = userDeckRepository;
    }
    public List<UserDeck> getAllDecksByUserId(String clerkUserId) {
        return userDeckRepository.findAllByClerkUserId(clerkUserId);
    }
    public UserDeck createDeck(String clerkUserId, UserDeck userDeck) {
        userDeck.setClerkUserId(clerkUserId);
        return userDeckRepository.save(userDeck);
    }
    public void deleteDeck(Long id) {
        userDeckRepository.deleteById(id);
    }
}
