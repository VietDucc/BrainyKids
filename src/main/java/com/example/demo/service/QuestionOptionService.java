package com.example.demo.service;

import com.example.demo.dto.request.QuestionOptionRequest;
import com.example.demo.entity.Question;
import com.example.demo.entity.QuestionOption;
import com.example.demo.repository.QuestionOptionRepository;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionOptionService {
    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionOption> getQuestionOptionsByQuestionId(Long questionId) {
        return questionOptionRepository.findByQuestion_Id(questionId);
    }

    public QuestionOption createQuestionOption(Long questionId, QuestionOptionRequest questionOptionRequest) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        QuestionOption questionOption = new QuestionOption();
        questionOption.setAnswers(questionOptionRequest.getAnswers());
        questionOption.setCorrect(questionOptionRequest.isCorrect());
        questionOption.setImgSrc(questionOptionRequest.getImgSrc());
        questionOption.setAudioSrc(questionOptionRequest.getAudioSrc());
        questionOption.setQuestion(question);

        return questionOptionRepository.save(questionOption);
    }

    public QuestionOption updateQuestionOption(Long optionId, QuestionOptionRequest questionOptionRequest) {
        QuestionOption questionOption = questionOptionRepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("Question Option not found"));

        Optional.ofNullable(questionOptionRequest.getAnswers()).ifPresent(questionOption::setAnswers);
        Optional.ofNullable(questionOptionRequest.isCorrect()).ifPresent(questionOption::setCorrect);
        Optional.ofNullable(questionOptionRequest.getImgSrc()).ifPresent(questionOption::setImgSrc);
        Optional.ofNullable(questionOptionRequest.getAudioSrc()).ifPresent(questionOption::setAudioSrc);

        return questionOptionRepository.save(questionOption);
    }

    public void deleteQuestionOption(Long optionId) {
        if (!questionOptionRepository.existsById(optionId)) {
            throw new RuntimeException("Question Option not found");
        }
        questionOptionRepository.deleteById(optionId);
    }
}