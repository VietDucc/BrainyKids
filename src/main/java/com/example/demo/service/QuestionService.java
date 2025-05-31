package com.example.demo.service;

import com.example.demo.dto.request.QuestionRequest;
import com.example.demo.dto.response.QuestionResponse;
import com.example.demo.entity.Question;
import com.example.demo.entity.TestPart;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.TestPartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TestPartRepository testPartRepository;

    public QuestionService(QuestionRepository questionRepository, TestPartRepository testPartRepository) {
        this.questionRepository = questionRepository;
        this.testPartRepository = testPartRepository;
    }

    public QuestionResponse createQuestion(Long partId, QuestionRequest request) {
        TestPart part = testPartRepository.findById(partId).orElseThrow(() -> new RuntimeException("Part not found"));
        Question question = Question.builder()
                .type(request.getType())
                .question(request.getQuestion())
                .questionImg(request.getQuestionImg())
                .choiceA(request.getChoiceA())
                .choiceB(request.getChoiceB())
                .choiceC(request.getChoiceC())
                .choiceD(request.getChoiceD())
                .choiceAImg(request.getChoiceAImg())
                .choiceBImg(request.getChoiceBImg())
                .choiceCImg(request.getChoiceCImg())
                .choiceDImg(request.getChoiceDImg())
                .correctAnswer(request.getCorrectAnswer())
                .part(part)
                .build();
        return mapToDto(questionRepository.save(question));
    }

    public QuestionResponse updateQuestion(Long questionId, QuestionRequest request) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
        question.setType(request.getType());
        question.setQuestion(request.getQuestion());
        question.setQuestionImg(request.getQuestionImg());
        question.setChoiceA(request.getChoiceA());
        question.setChoiceB(request.getChoiceB());
        question.setChoiceC(request.getChoiceC());
        question.setChoiceD(request.getChoiceD());
        question.setChoiceAImg(request.getChoiceAImg());
        question.setChoiceBImg(request.getChoiceBImg());
        question.setChoiceCImg(request.getChoiceCImg());
        question.setChoiceDImg(request.getChoiceDImg());
        question.setCorrectAnswer(request.getCorrectAnswer());
        return mapToDto(questionRepository.save(question));
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    private QuestionResponse mapToDto(Question q) {
        return QuestionResponse.builder()
                .id(q.getId())
                .type(q.getType())
                .question(q.getQuestion())
                .questionImg(q.getQuestionImg())
                .choiceA(q.getChoiceA())
                .choiceB(q.getChoiceB())
                .choiceC(q.getChoiceC())
                .choiceD(q.getChoiceD())
                .choiceAImg(q.getChoiceAImg())
                .choiceBImg(q.getChoiceBImg())
                .choiceCImg(q.getChoiceCImg())
                .choiceDImg(q.getChoiceDImg())
                .correctAnswer(q.getCorrectAnswer())
                .build();
    }
}
