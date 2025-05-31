package com.example.demo.service;

import com.example.demo.dto.request.QuestionRequest;
import com.example.demo.dto.request.TestPartRequest;
import com.example.demo.dto.request.TestRequest;
import com.example.demo.dto.request.TestSubmissionRequest;
import com.example.demo.dto.response.TestResponse;
import com.example.demo.dto.response.TestSubmissionResponse;
import com.example.demo.entity.Question;
import com.example.demo.entity.Test;
import com.example.demo.entity.TestPart;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.TestPartRepository;
import com.example.demo.repository.TestRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
@Builder
@RequiredArgsConstructor
public class TestService {
    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final TestPartRepository partRepository;

    public List<TestResponse> getAllTests() {
        return testRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TestResponse getTestById(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new RuntimeException("Test not found"));
        return mapToDto(test);
    }

    public TestResponse updateTest(Long id, TestRequest request) {
        Test test = testRepository.findById(id).orElseThrow(() -> new RuntimeException("Test not found"));
        test.setName(request.getName());
        test.setDescription(request.getDescription());
        return mapToDto(testRepository.save(test));
    }

    public TestSubmissionResponse testSubmit(TestSubmissionRequest request) {
        Test test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new IllegalArgumentException("test not found"));

        Map<Long, String> userAnswers = request.getAnswers().stream()
                .collect(Collectors.toMap(
                        TestSubmissionRequest.UserAnswerDTO::getQuestionId,
                        TestSubmissionRequest.UserAnswerDTO::getSelectedAnswer
                ));

        List<Question> allQuestions = test.getParts().stream()
                .flatMap(part -> part.getQuestions().stream())
                .collect(Collectors.toList());

        int total = allQuestions.size();
        int correct = 0;

        for (Question question : allQuestions) {
            String userAnswer = userAnswers.get(question.getId());
            if (userAnswer != null && userAnswer.equalsIgnoreCase(question.getCorrectAnswer())) {
                correct++;
            }
        }

        double score = ((double) correct / total) * 100.0;
        boolean passed = score >= 50.0;

        return new TestSubmissionResponse(passed, correct, total, score);
    }

    public TestResponse createTest(TestRequest dto) {
        Test test = new Test();
        test.setName(dto.getName());
        test.setDescription(dto.getDescription());

        List<TestPart> parts = new ArrayList<>();
        if (dto.getParts() != null) {
            for (TestPartRequest partDto : dto.getParts()) {
                TestPart part = new TestPart();
                part.setType(partDto.getType());
                part.setTest(test);

                List<Question> questions = new ArrayList<>();
                if (partDto.getQuestions() != null) {
                    for (QuestionRequest q : partDto.getQuestions()) {
                        Question question = Question.builder()
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
                                .part(part)
                                .build();
                        questions.add(question);
                    }
                }
                part.setQuestions(questions);
                parts.add(part);
            }
        }
        test.setParts(parts);
        testRepository.save(test);
        return mapToDto(test);
    }

    public TestResponse getTest(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new RuntimeException("Test not found"));
        return mapToDto(test);
    }

    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }

    private TestResponse mapToDto(Test test) {
        return TestResponse.builder()
                .id(test.getId())
                .name(test.getName())
                .description(test.getDescription())
                .parts(
                        test.getParts().stream().map(part ->
                                TestPartRequest.builder()
                                        .id(part.getId())
                                        .type(part.getType())
                                        .questions(
                                                part.getQuestions().stream().map(q -> QuestionRequest.builder()
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
                                                                .build())
                                                        .collect(Collectors.toList())
                                        )
                                        .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }
}
