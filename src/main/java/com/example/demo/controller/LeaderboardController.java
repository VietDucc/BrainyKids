package com.example.demo.controller;

import com.example.demo.dto.response.UserScoreResponseDTO;
import com.example.demo.service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    @Autowired
    private LeaderBoardService leaderboardService;

    @GetMapping
    public List<UserScoreResponseDTO> getAllUsers() {
        return leaderboardService.getAllUserScores();
    }
}