package com.example.demo.controller;

import com.example.demo.config.clerkauth.SecurityUtils;
import com.example.demo.dto.request.ChallengeRequest;
import com.example.demo.entity.User;
import com.example.demo.service.ChallengeService;
import com.example.demo.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ChallengeController {
    @Autowired
    private ChallengeService challengeService;
    private UserRepository userRepository;

    /**
     * createChallenge
     * @param challengeRequest
     * @return
     */
    @PostMapping("/challenge")
    public long createChallenge( @RequestBody ChallengeRequest challengeRequest) {
        String clerkUserId = SecurityUtils.getCurrentClerkUserId();

        User user = userRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admins only").getStatusCodeValue();
        }
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
        String clerkUserId = SecurityUtils.getCurrentClerkUserId();

        User user = userRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admins only").getStatusCodeValue();
        }
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
        String clerkUserId = SecurityUtils.getCurrentClerkUserId();

        User user = userRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admins only").getStatusCodeValue();
        }
        return challengeService.deleteChallenge(challengeId);
    }


}
