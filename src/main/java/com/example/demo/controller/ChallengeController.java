package com.example.demo.controller;

import com.example.demo.dto.request.ChallengeRequest;
import com.example.demo.entity.Challenge;
import com.example.demo.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {
    @Autowired
    private ChallengeService challengeService;

    @GetMapping("/{lessonId}")
    public List<Challenge> getChallengeByLessonId(@PathVariable Long lessonId) {
        return challengeService.getChallengeByLessonId(lessonId);
    }

    @PostMapping("/{lessonId}")
    public Challenge createChallenge(@PathVariable Long lessonId, @RequestBody ChallengeRequest challengeRequest) {
       return challengeService.createChallenge(lessonId, challengeRequest);
    }
    @PutMapping("/{challengeId}")
    public Challenge updateChallenge(@PathVariable Long challengeId, @RequestBody ChallengeRequest challengeRequest) {
        return challengeService.updateChallenge(challengeId, challengeRequest);
    }
    @DeleteMapping("/{challengeId}")
    public void deleteChallenge(@PathVariable Long challengeId) {
        challengeService.deleteChallenge(challengeId);
    }

}
