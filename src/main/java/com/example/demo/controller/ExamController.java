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
@RequestMapping("/api/exam")
@CrossOrigin(origins = "*")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private PartService partService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionOptionService questionOptionService;

    @Autowired
    private QuestionProgressService questionProgressService;

    @Autowired
    private QuestionCacheService questionCacheService;


    @GetMapping("/exams")
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

    @GetMapping("/exams/{examId}")
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

    @PostMapping("/exams")
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamRequest examRequest) {
        try {
            Exam exam = examService.createExam(examRequest);
            ExamDTO examDTO = convertToExamDTO(exam);
            return ResponseEntity.status(HttpStatus.CREATED).body(examDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/exams/{examId}")
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

    @DeleteMapping("/exams/{examId}")
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

    @GetMapping("/exams/{examId}/parts")
    public ResponseEntity<List<PartDTO>> getPartsByExamId(@PathVariable Long examId) {
        try {
            List<Part> parts = partService.getPartsByExamId(examId);
            List<PartDTO> partDTOs = parts.stream()
                    .map(this::convertToPartDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(partDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/parts")
    public ResponseEntity<PartDTO> createPart(@RequestBody PartRequest partRequest) {
        try {
            Part part = partService.createPart(partRequest);
            PartDTO partDTO = convertToPartDTO(part);
            return ResponseEntity.status(HttpStatus.CREATED).body(partDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/parts/{partId}")
    public ResponseEntity<PartDTO> updatePart(@PathVariable Long partId, @RequestBody PartRequest partRequest) {
        try {
            Part part = partService.updatePart(partId, partRequest);
            PartDTO partDTO = convertToPartDTO(part);
            return ResponseEntity.ok(partDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/parts/{partId}")
    public ResponseEntity<Map<String, String>> deletePart(@PathVariable Long partId) {
        try {
            partService.deletePart(partId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Part deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/parts/{partId}/questions")
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

    @PostMapping("/questions")
    public ResponseEntity<Map<String, Long>> createQuestion(@RequestBody QuestionRequest questionRequest) {
        try {
            long questionId = questionService.createQuestion(questionRequest);
            Map<String, Long> response = new HashMap<>();
            response.put("questionId", questionId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/questions/{questionId}")
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

    @DeleteMapping("/questions/{questionId}")
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

    @PostMapping(value = "/questions/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public ResponseEntity<QuestionOptionDTO> createQuestionOption(@PathVariable Long questionId, @RequestBody QuestionOptionRequest optionRequest) {
        try {
            QuestionOption option = questionOptionService.createQuestionOption(questionId, optionRequest);
            QuestionOptionDTO optionDTO = convertToQuestionOptionDTO(option);
            return ResponseEntity.status(HttpStatus.CREATED).body(optionDTO);
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