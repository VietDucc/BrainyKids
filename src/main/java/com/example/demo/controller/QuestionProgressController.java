package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.*;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exam")
public class QuestionProgressController {

    @Autowired
    private QuestionProgressService questionProgressService;

    @Autowired
    private QuestionCacheService questionCacheService;

    @GetMapping("/progress/user/{userId}")
    public ResponseEntity<List<QuestionProgress>> getUserProgress(@PathVariable String userId) {
        try {
            List<QuestionProgress> progress = questionProgressService.getQuestionProgressByUserId(userId);
            return ResponseEntity.ok(progress);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/progress/question/{questionId}")
    public ResponseEntity<List<QuestionProgress>> getQuestionProgress(@PathVariable Long questionId) {
        try {
            List<QuestionProgress> progress = questionProgressService.getQuestionProgressByQuestionId(questionId);
            return ResponseEntity.ok(progress);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/progress/question/{questionId}")
    public ResponseEntity<QuestionProgress> createQuestionProgress(@PathVariable Long questionId, @RequestBody QuestionProgressRequest progressRequest) {
        try {
            QuestionProgress progress = questionProgressService.createQuestionProgress(questionId, progressRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(progress);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/progress/{progressId}")
    public ResponseEntity<QuestionProgress> updateQuestionProgress(@PathVariable Long progressId, @RequestBody QuestionProgressRequest progressRequest) {
        try {
            QuestionProgress progress = questionProgressService.updateQuestionProgress(progressId, progressRequest);
            return ResponseEntity.ok(progress);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/progress/user/{userId}/question/{questionId}")
    public ResponseEntity<QuestionProgress> updateUserQuestionProgress(
            @PathVariable String userId,
            @PathVariable Long questionId,
            @RequestParam boolean completed) {
        try {
            QuestionProgress progress = questionProgressService.updateQuestionProgressByUserAndQuestion(userId, questionId, completed);
            return ResponseEntity.ok(progress);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/progress/{progressId}")
    public ResponseEntity<Map<String, String>> deleteQuestionProgress(@PathVariable Long progressId) {
        try {
            questionProgressService.deleteQuestionProgress(progressId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Question progress deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/progress/user/{userId}/exam/{examId}")
    public ResponseEntity<Map<String, Double>> getUserExamProgress(@PathVariable String userId, @PathVariable Long examId) {
        try {
            double progressPercentage = questionProgressService.calculateUserProgressForExam(userId, examId);
            Map<String, Double> response = new HashMap<>();
            response.put("progressPercentage", progressPercentage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cache/refresh/part/{partId}")
    public ResponseEntity<Map<String, String>> refreshQuestionCache(@PathVariable Long partId) {
        try {
            questionCacheService.refreshQuestionsByPartId(partId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cache refreshed successfully for part " + partId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/cache/evict/part/{partId}")
    public ResponseEntity<Map<String, String>> evictQuestionCache(@PathVariable Long partId) {
        try {
            questionCacheService.evictQuestionCache(partId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cache evicted successfully for part " + partId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
