package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.LeaderBoardService;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {
    private final LeaderBoardService leaderBoardService;

    public LeaderboardController(LeaderBoardService leaderBoardService) {
        this.leaderBoardService = leaderBoardService;
    }

    @GetMapping("/top100")
    public List<User> getTop100Users() {
        return leaderBoardService.getTop100Users();
    }
}
