package com.example.demo.repository;

import com.example.demo.entity.ChallengeProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeProgressRepository extends JpaRepository<ChallengeProgress, Long> {
    List<ChallengeProgress> findByChallenge_Id(Long ChallengeId);
}
