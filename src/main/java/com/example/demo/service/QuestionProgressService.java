package com.example.demo.service;

import com.example.demo.dto.request.QuestionProgressRequest;
import com.example.demo.entity.Question;
import com.example.demo.entity.QuestionProgress;
import com.example.demo.repository.QuestionProgressRepository;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionProgressService {
    @Autowired
    private QuestionProgressRepository questionProgressRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionProgress> getQuestionProgressByQuestionId(Long questionId) {
        return questionProgressRepository.findByQuestion_Id(questionId);
    }

    public List<QuestionProgress> getQuestionProgressByUserId(String userId) {
        return questionProgressRepository.findByUserId(userId);
    }

    public QuestionProgress createQuestionProgress(Long questionId, QuestionProgressRequest questionProgressRequest) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Check if progress already exists for this user and question
        Optional<QuestionProgress> existingProgress = questionProgressRepository
                .findByUserIdAndQuestionId(questionProgressRequest.getUserId(), questionId);

        if (existingProgress.isPresent()) {
            throw new RuntimeException("Progress already exists for this user and question");
        }

        QuestionProgress questionProgress = new QuestionProgress();
        questionProgress.setUserId(questionProgressRequest.getUserId());
        questionProgress.setCompleted(questionProgressRequest.isCompleted());
        questionProgress.setQuestion(question);

        return questionProgressRepository.save(questionProgress);
    }

    public QuestionProgress updateQuestionProgress(Long progressId, QuestionProgressRequest questionProgressRequest) {
        QuestionProgress questionProgress = questionProgressRepository.findById(progressId)
                .orElseThrow(() -> new RuntimeException("Question Progress not found"));

        Optional.ofNullable(questionProgressRequest.getUserId()).ifPresent(questionProgress::setUserId);
        questionProgress.setCompleted(questionProgressRequest.isCompleted());

        return questionProgressRepository.save(questionProgress);
    }

    public QuestionProgress updateQuestionProgressByUserAndQuestion(String userId, Long questionId, boolean completed) {
        Optional<QuestionProgress> existingProgress = questionProgressRepository
                .findByUserIdAndQuestionId(userId, questionId);

        if (existingProgress.isPresent()) {
            QuestionProgress progress = existingProgress.get();
            progress.setCompleted(completed);
            return questionProgressRepository.save(progress);
        } else {
            // Create new progress if doesn't exist
            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            QuestionProgress questionProgress = new QuestionProgress();
            questionProgress.setUserId(userId);
            questionProgress.setCompleted(completed);
            questionProgress.setQuestion(question);

            return questionProgressRepository.save(questionProgress);
        }
    }

    public void deleteQuestionProgress(Long progressId) {
        if (!questionProgressRepository.existsById(progressId)) {
            throw new RuntimeException("Question Progress not found");
        }
        questionProgressRepository.deleteById(progressId);
    }

    public double calculateUserProgressForExam(String userId, Long examId) {
        List<Question> allQuestionsInExam = questionRepository.findByPart_Exam_Id(examId);
        if (allQuestionsInExam.isEmpty()) {
            return 0.0;
        }

        List<QuestionProgress> userProgress = questionProgressRepository.findByUserId(userId);
        long completedQuestions = userProgress.stream()
                .filter(progress -> allQuestionsInExam.stream()
                        .anyMatch(q -> q.getId().equals(progress.getQuestion().getId())))
                .mapToLong(progress -> progress.isCompleted() ? 1 : 0)
                .sum();

        return (double) completedQuestions / allQuestionsInExam.size() * 100;
    }
}