package com.example.demo.service;

import com.example.demo.dto.request.AnswerRequest;
import com.example.demo.dto.response.AnswerResponse;
import com.example.demo.dto.response.QuestionResponse;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Question;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.TestPartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public AnswerResponse createAnswer(Long questionId, AnswerRequest answerRequest) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        if (question.getAnswer() != null) {
            throw new RuntimeException("Question already has an answer");
        }

        Answer answer = Answer.builder()
                .choiceA(answerRequest.getChoiceA())
                .choiceB(answerRequest.getChoiceB())
                .choiceC(answerRequest.getChoiceC())
                .choiceD(answerRequest.getChoiceD())
                .choiceAImg(answerRequest.getChoiceAImg())
                .choiceBImg(answerRequest.getChoiceBImg())
                .choiceCImg(answerRequest.getChoiceCImg())
                .choiceDImg(answerRequest.getChoiceDImg())
                .correctAnswer(answerRequest.getCorrectAnswer())
                .question(question)
                .build();

        question.setAnswer(answer);

        Question savedQuestion = questionRepository.save(question);

        return mapToDto(savedQuestion.getAnswer());
    }


    public AnswerResponse getAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new RuntimeException("Answer not found"));
        return mapToDto(answer);
    }

    @Transactional
    public AnswerResponse updateAnswer(Long answerId, AnswerRequest answerRequest) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new RuntimeException("Answer not found"));
        answer.setChoiceA(answerRequest.getChoiceA());
        answer.setChoiceB(answerRequest.getChoiceB());
        answer.setChoiceC(answerRequest.getChoiceC());
        answer.setChoiceD(answerRequest.getChoiceD());
        answer.setChoiceAImg(answerRequest.getChoiceAImg());
        answer.setChoiceBImg(answerRequest.getChoiceBImg());
        answer.setChoiceCImg(answerRequest.getChoiceCImg());
        answer.setChoiceDImg(answerRequest.getChoiceDImg());
        answer.setCorrectAnswer(answerRequest.getCorrectAnswer());
        return mapToDto(answerRepository.save(answer));
    }

    public void deleteAnswer(Long answerId) {
        answerRepository.deleteById(answerId);
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
