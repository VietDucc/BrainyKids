package com.example.demo.repository;

import com.example.demo.dto.response.UserDeckResponse;
import com.example.demo.entity.UserDeck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDeckRepository extends JpaRepository<UserDeck, Long> {
    List<UserDeck> findAllByClerkUserId(String clerkUserId);
    Optional<UserDeck> findByIdAndClerkUserId(Long id, String clerkUserId);
    UserDeck findUserDeckById(int id);
}
