package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.*;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exam/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionCacheService questionCacheService;

    @GetMapping("/{partId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByPartId(@PathVariable Long partId) {
        try {
            List<Question> questions = questionCacheService.getQuestionsByPartId(partId);
            List<QuestionDTO> questionDTOs = questions.stream()
                    .map(this::convertToQuestionDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(questionDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createQuestion(@RequestBody QuestionRequest questionRequest) {
        try {
            System.out.println("Received question request: " + questionRequest);
            System.out.println("Question type: " + questionRequest.getType());
            System.out.println("Question options: " + questionRequest.getQuestionOptions());

            questionService.createQuestion(questionRequest);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println("RuntimeException in createQuestion: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.out.println("Exception in createQuestion: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Map<String, Long>> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionRequest questionRequest) {
        try {
            long updatedQuestionId = questionService.updateQuestion(questionId, questionRequest);
            Map<String, Long> response = new HashMap<>();
            response.put("questionId", updatedQuestionId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Map<String, String>> deleteQuestion(@PathVariable Long questionId) {
        try {
            questionService.deleteQuestion(questionId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Question deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Import questions from Excel file")
    public ResponseEntity<Map<String, String>> importQuestionsFromExcel(
            @Parameter(description = "Excel file containing questions")
            @RequestParam("file") MultipartFile file)
    {
        try {
            if (file.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Please select a file to upload");
                return ResponseEntity.badRequest().body(error);
            }

            questionService.importQuestionsFromExcel(file);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Questions imported successfully from Excel file");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to import questions: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
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
