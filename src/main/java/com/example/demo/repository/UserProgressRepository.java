package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    Optional<UserProgress> findByClerkUserId(String clerkUserId);
}
