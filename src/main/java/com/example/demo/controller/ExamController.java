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
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamProgressService examProgressService;

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        try {
            List<Exam> exams = examService.getAllExams();
            List<ExamDTO> examDTOs = exams.stream()
                    .map(this::convertToExamDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(examDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{examId}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable Long examId) {
        try {
            Exam exam = examService.getExamById(examId);
            ExamDTO examDTO = convertToExamDTO(exam);
            return ResponseEntity.ok(examDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createExam(@RequestBody ExamRequest examRequest) {
        try {
            examService.createExam(examRequest);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{examId}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable Long examId, @RequestBody ExamRequest examRequest) {
        try {
            Exam exam = examService.updateExam(examId, examRequest);
            ExamDTO examDTO = convertToExamDTO(exam);
            return ResponseEntity.ok(examDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Map<String, String>> deleteExam(@PathVariable Long examId) {
        try {
            examService.deleteExam(examId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Exam deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ExamDTO convertToExamDTO(Exam exam) {
        List<PartDTO> partDTOs = exam.getParts().stream()
                .map(this::convertToPartDTO)
                .collect(Collectors.toList());

        return ExamDTO.builder()
                .id(exam.getId())
                .name(exam.getName())
                .description(exam.getDescription())
                .voice(exam.getVoice())
                .parts(partDTOs)
                .build();
    }

    private PartDTO convertToPartDTO(Part part) {
        List<QuestionDTO> questionDTOs = part.getQuestions().stream()
                .map(this::convertToQuestionDTO)
                .collect(Collectors.toList());

        return PartDTO.builder()
                .id(part.getId())
                .description(part.getDescription())
                .partOrder(part.getPartOrder())
                .partNumber(part.getPartNumber())
                .type(part.getType())
                .questions(questionDTOs)
                .build();
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