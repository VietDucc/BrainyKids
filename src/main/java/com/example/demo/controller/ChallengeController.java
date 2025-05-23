package com.example.demo.controller;

import com.example.demo.dto.request.ChallengeRequest;
import com.example.demo.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // Post nhieu cau hoi bang file excel
    @Operation(summary = "Import challenges from Excel file")
    @PostMapping(value = "/challenge/multi", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importChallenges(
            @RequestParam("file") MultipartFile file
    ) {
        challengeService.importChallengesFromExcel(file);
        return ResponseEntity.ok("Import successful");
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


}
