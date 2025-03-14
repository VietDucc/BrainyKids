package com.example.demo.controller;


import com.example.demo.dto.request.ChallengeOptionRequest;
import com.example.demo.entity.ChallengeOption;
import com.example.demo.service.ChallengeOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenge-options")
public class ChallengeOptionsController {
    @Autowired
    private ChallengeOptionService challengeOptionService;

    @GetMapping("/{challengeId}")
    public List<ChallengeOption> getChallengeOptionsByChallengeId(Long challengeId) {
        return challengeOptionService.getChallengeOptionsByChallengeId(challengeId);
    }

    @PostMapping("/{challengeId}")
    public ChallengeOption createChallengeOption(@PathVariable Long challengeId,@RequestBody ChallengeOptionRequest challengeOptionRequest) {
        return challengeOptionService.createChallengeOption(challengeId, challengeOptionRequest);
    }
}
