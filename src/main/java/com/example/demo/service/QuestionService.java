package com.example.demo.service;

import com.example.demo.dto.request.QuestionRequest;
import com.example.demo.dto.response.AnswerResponse;
import com.example.demo.dto.response.QuestionResponse;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Question;
import com.example.demo.entity.TestPart;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.TestPartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TestPartRepository testPartRepository;

    public QuestionResponse createQuestion(Long partId, QuestionRequest request) {
        TestPart part = testPartRepository.findById(partId).orElseThrow(() -> new RuntimeException("Part not found"));
        Question question = Question.builder()
                .type(request.getType())
                .question(request.getQuestion())
                .questionImg(request.getQuestionImg())
                .part(part)
                .id(request.getId())
                .answer(request.getAnswer())
                .build();
        return mapToDto(questionRepository.save(question));
    }

    public QuestionResponse updateQuestion(Long questionId, QuestionRequest request) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
        question.setType(request.getType());
        question.setQuestion(request.getQuestion());
        question.setQuestionImg(request.getQuestionImg());
        question.setDescription(request.getDescription());
        question.setQuestionOrder(question.getQuestionOrder());
        return mapToDto(questionRepository.save(question));
    }

    public QuestionResponse getQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
        return mapToDto(question);
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
                .description(q.getDescription())
                .questionOrder(q.getQuestionOrder())
                .answer(mapToDto(q.getAnswer()))
                .build();
    }

    private AnswerResponse mapToDto(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .choiceA(answer.getChoiceA())
                .choiceB(answer.getChoiceB())
                .choiceC(answer.getChoiceC())
                .choiceD(answer.getChoiceD())
                .choiceAImg(answer.getChoiceAImg())
                .choiceBImg(answer.getChoiceBImg())
                .choiceCImg(answer.getChoiceCImg())
                .choiceDImg(answer.getChoiceDImg())
                .correctAnswer(answer.getCorrectAnswer())
                .build();
    }
}
