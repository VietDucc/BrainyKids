package com.example.demo.controller;

import com.example.demo.dto.request.ChallengeProgressRequest;
import com.example.demo.entity.ChallengeProgress;
import com.example.demo.service.ChallengeProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenge-progress")
public class ChallengeProgressController {
    @Autowired
    private ChallengeProgressService challengeProgressService;

    @GetMapping("/{challengeId}")
    public List<ChallengeProgress> getChallengeProgressByChallengeId(@PathVariable Long challengeId) {
        return challengeProgressService.getChallengeProgressByChallengeId(challengeId);
    }

    @PostMapping("/{challengeId}")
    public ChallengeProgress createChallengeProgress(@PathVariable Long challengeId, @RequestBody ChallengeProgressRequest challengeProgressRequest) {
        return challengeProgressService.createChallengeProgress(challengeId, challengeProgressRequest);
    }

    @PutMapping("/{progressId}")
    public ChallengeProgress updateChallengeProgress(@PathVariable Long progressId, @RequestBody ChallengeProgressRequest challengeProgressRequest) {
        return challengeProgressService.updateChallengeProgress(progressId, challengeProgressRequest);
    }

    @DeleteMapping("/{progressId}")
    public void deleteChallengeProgress(@PathVariable Long progressId) {
        challengeProgressService.deleteChallengeProgress(progressId);
    }
}
