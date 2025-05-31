package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.*;
import com.example.demo.service.AnswerService;
import com.example.demo.service.QuestionService;
import com.example.demo.service.TestPartService;
import com.example.demo.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    private final TestPartService testPartService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<TestResponse> createTest(@RequestBody TestRequest request) {
        return ResponseEntity.ok(testService.createTest(request));
    }

    @GetMapping
    public ResponseEntity<List<TestResponse>> getAllTests() {
        return ResponseEntity.ok(testService.getAllTests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResponse> getTestById(@PathVariable Long id) {
        return ResponseEntity.ok(testService.getTestById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestResponse> updateTest(@PathVariable Long id, @RequestBody TestRequest request) {
        return ResponseEntity.ok(testService.updateTest(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{testId}/parts")
    public ResponseEntity<TestPartResponse> createTestPart(@PathVariable Long testId, @RequestBody TestPartRequest request) {
        return ResponseEntity.ok(testPartService.createPart(testId, request));
    }

    @PutMapping("/parts/{partId}")
    public ResponseEntity<TestPartResponse> updateTestPart(@PathVariable Long partId, @RequestBody TestPartRequest request) {
        return ResponseEntity.ok(testPartService.updatePart(partId, request));
    }

    @DeleteMapping("/parts/{partId}")
    public ResponseEntity<Void> deleteTestPart(@PathVariable Long partId) {
        testPartService.deletePart(partId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/parts/{partId}/questions")
    public ResponseEntity<QuestionResponse> createQuestion(@PathVariable Long partId, @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(questionService.createQuestion(partId, request));
    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(questionService.updateQuestion(questionId, request));
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/questions/{questionId}/answers")
    public ResponseEntity<AnswerResponse> createAnswer(@PathVariable Long questionId, @RequestBody AnswerRequest request) {
        return ResponseEntity.ok(answerService.createAnswer(questionId, request));
    }

    @PutMapping("/answers/{answerId}")
    public ResponseEntity<AnswerResponse> updateAnswer(@PathVariable Long answerId, @RequestBody AnswerRequest request) {
        return ResponseEntity.ok(answerService.updateAnswer(answerId, request));
    }

    @DeleteMapping("/answers/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{testId}/submit")
    public ResponseEntity<TestSubmissionResponse> submitTest(@PathVariable Long testId, @RequestBody TestSubmissionRequest request) {
        return ResponseEntity.ok(testService.testSubmit(request));
    }
}
