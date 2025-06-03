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
@RequestMapping("/api/exam/options")
public class QuestionOptionController {

    @Autowired
    private QuestionOptionService questionOptionService;

    @GetMapping("/questions/{questionId}/options")
    public ResponseEntity<List<QuestionOptionDTO>> getQuestionOptionsByQuestionId(@PathVariable Long questionId) {
        try {
            List<QuestionOption> options = questionOptionService.getQuestionOptionsByQuestionId(questionId);
            List<QuestionOptionDTO> optionDTOs = options.stream()
                    .map(this::convertToQuestionOptionDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(optionDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/questions/{questionId}/options")
    public ResponseEntity<Map<String, String>> createQuestionOption(@PathVariable Long questionId, @RequestBody QuestionOptionRequest optionRequest) {
        try {
            questionOptionService.createQuestionOption(questionId, optionRequest);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/options/{optionId}")
    public ResponseEntity<QuestionOptionDTO> updateQuestionOption(@PathVariable Long optionId, @RequestBody QuestionOptionRequest optionRequest) {
        try {
            QuestionOption option = questionOptionService.updateQuestionOption(optionId, optionRequest);
            QuestionOptionDTO optionDTO = convertToQuestionOptionDTO(option);
            return ResponseEntity.ok(optionDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/options/{optionId}")
    public ResponseEntity<Map<String, String>> deleteQuestionOption(@PathVariable Long optionId) {
        try {
            questionOptionService.deleteQuestionOption(optionId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Question option deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private QuestionDTO convertToQuestionDTO(Question question) {
        List<QuestionOptionDTO> optionDTOs = question.getQuestionOptions().stream()
                .map(this::convertToQuestionOptionDTO)
                .collect(Collectors.toList());

        return QuestionDTO.builder()
                .id(question.getId())
                .type(question.getType())
                .question(question.getQuestion())
                .description(question.getDescription())
                .imgSrc(question.getImgSrc())
                .questionOrder(question.getQuestionOrder())
                .questionOptions(optionDTOs)
                .build();
    }

    private QuestionOptionDTO convertToQuestionOptionDTO(QuestionOption option) {
        return QuestionOptionDTO.builder()
                .id(option.getId())
                .answers(option.getAnswers())
                .correct(option.isCorrect())
                .imgSrc(option.getImgSrc())
                .audioSrc(option.getAudioSrc())
                .build();
    }
}
