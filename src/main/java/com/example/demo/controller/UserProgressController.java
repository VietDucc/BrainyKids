package com.example.demo.controller;

import com.example.demo.service.UserProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/streak")

public class UserProgressController {
    @Autowired
    private UserProgressService userProgressService;

    @GetMapping("/{clerkUserId}")
    public ResponseEntity<Integer> getStreak(@PathVariable String clerkUserId) {
        int streak = userProgressService.getUserStreak(clerkUserId);
        return ResponseEntity.ok(streak);
    }
}
