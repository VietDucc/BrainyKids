package com.example.demo.service;

import com.example.demo.dto.request.ExamRequest;
import com.example.demo.entity.Exam;
import com.example.demo.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(Long examId) {
        return examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
    }

    public Exam createExam(ExamRequest examRequest) {
        Exam exam = Exam.builder()
                .name(examRequest.getName())
                .description(examRequest.getDescription())
                .voice(examRequest.getVoice())
                .build();
        return examRepository.save(exam);
    }

    public Exam updateExam(Long examId, ExamRequest examRequest) {
        Exam exam = getExamById(examId);
        exam.setName(examRequest.getName());
        exam.setDescription(examRequest.getDescription());
        exam.setVoice(examRequest.getVoice());
        return examRepository.save(exam);
    }

    public void deleteExam(Long examId) {
        if (!examRepository.existsById(examId)) {
            throw new RuntimeException("Exam not found");
        }
        examRepository.deleteById(examId);
    }
}