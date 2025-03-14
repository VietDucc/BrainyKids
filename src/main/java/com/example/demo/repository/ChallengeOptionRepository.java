package com.example.demo.repository;

import com.example.demo.entity.ChallengeOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeOptionRepository extends JpaRepository<ChallengeOption, Long> {
    List<ChallengeOption> findByChallenge_Id(Long ChallengeId);
}
