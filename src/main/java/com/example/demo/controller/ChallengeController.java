package com.example.demo.controller;

import com.example.demo.dto.request.ChallengeRequest;
import com.example.demo.entity.Challenge;
import com.example.demo.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChallengeController {
    @Autowired
    private ChallengeService challengeService;
    /**
     * createChallenge
     * @param challengeRequest
     * @return
     */
    @PostMapping("/challenge")
    public long createChallenge( @RequestBody ChallengeRequest challengeRequest) {
        return challengeService.createChallenge( challengeRequest);
    }

    /**
     * updateChallenge
     * @param challengeId
     * @param challengeRequest
     * @return
     */
    @PutMapping("/challenge/{challengeId}")
    public long updateChallenge (@PathVariable long challengeId, @RequestBody ChallengeRequest challengeRequest)
    {
        return challengeService.updateChallenge(challengeId, challengeRequest);
    }

    /**
     * deleteChallenge
     * @param challengeId
     * @return
     */
    @DeleteMapping("/challenge/{challengeId}")
    public long deleteChallenge (@PathVariable long challengeId)
    {
        return challengeService.deleteChallenge(challengeId);
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
