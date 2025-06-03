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
public class ExamProgressController {

    @Autowired
    private ExamProgressService examProgressService;

    @GetMapping("/progress/exam/{examId}")
    public ResponseEntity<List<ExamProgress>> getExamProgress(@PathVariable Long examId) {
        try {
            List<ExamProgress> progress = examProgressService.getExamProgressByExamId(examId);
            return ResponseEntity.ok(progress);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/progress/exam/{examId}")
    public ResponseEntity<ExamProgress> createExamProgress(
            @PathVariable Long examId,
            @RequestBody ExamProgressRequest progressRequest) {
        try {
            ExamProgress progress = examProgressService.createExamProgress(progressRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(progress);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/progress/exam/{progressId}")
    public ResponseEntity<ExamProgress> updateExamProgress(
            @PathVariable Long progressId,
            @RequestBody ExamProgressRequest progressRequest) {
        try {
            ExamProgress progress = examProgressService.updateExamProgress(progressId, progressRequest);
            return ResponseEntity.ok(progress);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/progress/exam/{progressId}")
    public ResponseEntity<Map<String, String>> deleteExamProgress(@PathVariable Long progressId) {
        try {
            examProgressService.deleteExamProgress(progressId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Exam progress deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/progress/user/{userId}/exam-status/{examId}")
    public ResponseEntity<Map<String, Double>> getUserExamProgressStatus(
            @PathVariable String userId,
            @PathVariable Long examId) {
        try {
            double progressPercentage = examProgressService.calculateUserProgressForExam(userId, examId);
            Map<String, Double> response = new HashMap<>();
            response.put("progressPercentage", progressPercentage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
