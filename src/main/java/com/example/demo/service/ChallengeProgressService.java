package com.example.demo.service;

import com.example.demo.dto.request.ChallengeProgressRequest;
import com.example.demo.entity.Challenge;
import com.example.demo.entity.ChallengeProgress;
import com.example.demo.repository.ChallengeProgressRepository;
import com.example.demo.repository.ChallengeRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChallengeProgressService {
    @Autowired
    private ChallengeProgressRepository challengeProgressRepository; // Sửa tên đúng

    @Autowired
    private ChallengeRepository challengeRepository;



    public List<ChallengeProgress> getChallengeProgressByChallengeId(Long challengeId) {
        return challengeProgressRepository.findByChallenge_Id(challengeId);
    }

    public ChallengeProgress createChallengeProgress(Long challengeId, ChallengeProgressRequest challengeProgressRequest) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));


        ChallengeProgress challengeProgress = new ChallengeProgress();
        challengeProgress.setUserId(challengeProgressRequest.getUserId());
        challengeProgress.setCompleted(challengeProgressRequest.isCompleted());
        challengeProgress.setChallenge(challenge);

        return challengeProgressRepository.save(challengeProgress);
    }

    public ChallengeProgress updateChallengeProgress(Long progressId, ChallengeProgressRequest challengeProgressRequest) {
        ChallengeProgress challengeProgress = challengeProgressRepository.findById(progressId)
                .orElseThrow(() -> new RuntimeException("Challenge Progress not found"));

        Optional.ofNullable(challengeProgressRequest.getUserId()).ifPresent(challengeProgress::setUserId);
        Optional.ofNullable(challengeProgressRequest.isCompleted()).ifPresent(challengeProgress::setCompleted);

        return challengeProgressRepository.save(challengeProgress);
    }


    public void deleteChallengeProgress(Long progressId) {
        if (!challengeProgressRepository.existsById(progressId)) {
            throw new RuntimeException("Challenge Progress not found");
        }
        challengeProgressRepository.deleteById(progressId);
    }
}
