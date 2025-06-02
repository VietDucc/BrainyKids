package com.example.demo.service;

import com.example.demo.dto.request.ExamProgressRequest;
import com.example.demo.entity.Exam;
import com.example.demo.entity.ExamProgress;
import com.example.demo.repository.ExamProgressRepository;
import com.example.demo.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamProgressService {

    @Autowired
    private ExamProgressRepository examProgressRepository;

    @Autowired
    private ExamRepository examRepository;

    public List<ExamProgress> getExamProgressByUserId(String userId) {
        return examProgressRepository.findByUserId(userId);
    }

    public List<ExamProgress> getExamProgressByExamId(Long examId) {
        return examProgressRepository.findByExam_Id(examId);
    }

    public ExamProgress createExamProgress(ExamProgressRequest request) {
        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        ExamProgress progress = ExamProgress.builder()
                .userId(request.getUserId())
                .exam(exam)
                .completedQuestions(request.getCompletedQuestions())
                .totalQuestions(request.getTotalQuestions())
                .score(request.getScore())
                .completed(request.isCompleted())
                .startedAt(LocalDateTime.now())
                .completedAt(request.isCompleted() ? LocalDateTime.now() : null)
                .build();

        return examProgressRepository.save(progress);
    }

    public ExamProgress updateExamProgress(Long progressId, ExamProgressRequest request) {
        ExamProgress progress = examProgressRepository.findById(progressId)
                .orElseThrow(() -> new RuntimeException("Exam progress not found"));

        progress.setCompletedQuestions(request.getCompletedQuestions());
        progress.setTotalQuestions(request.getTotalQuestions());
        progress.setScore(request.getScore());

        if (!progress.isCompleted() && request.isCompleted()) {
            progress.setCompletedAt(LocalDateTime.now());
        }
        progress.setCompleted(request.isCompleted());

        return examProgressRepository.save(progress);
    }

    public void deleteExamProgress(Long progressId) {
        if (!examProgressRepository.existsById(progressId)) {
            throw new RuntimeException("Exam progress not found");
        }
        examProgressRepository.deleteById(progressId);
    }

    public ExamProgress updateExamProgressByUserAndExam(String userId, Long examId,
                                                        Integer completedQuestions, Double score, boolean completed) {
        ExamProgress progress = examProgressRepository.findByUserIdAndExam_Id(userId, examId)
                .orElseThrow(() -> new RuntimeException("Exam progress not found"));

        progress.setCompletedQuestions(completedQuestions);
        progress.setScore(score);

        if (!progress.isCompleted() && completed) {
            progress.setCompletedAt(LocalDateTime.now());
        }
        progress.setCompleted(completed);

        return examProgressRepository.save(progress);
    }

    public double calculateUserProgressForExam(String userId, Long examId) {
        ExamProgress progress = examProgressRepository.findByUserIdAndExam_Id(userId, examId)
                .orElse(null);

        if (progress == null || progress.getTotalQuestions() == 0) {
            return 0.0;
        }

        return (progress.getCompletedQuestions() * 100.0) / progress.getTotalQuestions();
    }
}