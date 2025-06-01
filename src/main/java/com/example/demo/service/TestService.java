package com.example.demo.service;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.*;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Question;
import com.example.demo.entity.Test;
import com.example.demo.entity.TestPart;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.TestPartRepository;
import com.example.demo.repository.TestRepository;
import jakarta.transaction.Transactional;
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
    private final AnswerRepository answerRepository;

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
            if (userAnswer != null && userAnswer.equalsIgnoreCase(question.getAnswer().getCorrectAnswer())) {
                correct++;
            }
        }

        double score = ((double) correct / total) * 100.0;
        boolean passed = score >= 50.0;

        return new TestSubmissionResponse(passed, correct, total, score);
    }

    @Transactional
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
                                .description(q.getDescription())
                                .questionOrder(q.getQuestionOrder())
                                .part(part)
                                .build();

                        if (q.getAnswer() != null) {
                            Answer answer = Answer.builder()
                                    .choiceA(q.getAnswer().getChoiceA())
                                    .choiceB(q.getAnswer().getChoiceB())
                                    .choiceC(q.getAnswer().getChoiceC())
                                    .choiceD(q.getAnswer().getChoiceD())
                                    .choiceAImg(q.getAnswer().getChoiceAImg())
                                    .choiceBImg(q.getAnswer().getChoiceBImg())
                                    .choiceCImg(q.getAnswer().getChoiceCImg())
                                    .choiceDImg(q.getAnswer().getChoiceDImg())
                                    .correctAnswer(q.getAnswer().getCorrectAnswer())
                                    .question(question)
                                    .build();

                            question.setAnswer(answer);
                        }

                        questions.add(question);
                    }
                }
                part.setQuestions(questions);
                parts.add(part);
            }
        }
        test.setParts(parts);

        Test savedTest = testRepository.save(test);
        return mapToDto(savedTest);
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
                                TestPartResponse.builder()
                                        .id(part.getId())
                                        .type(part.getType())
                                        .questions(
                                                part.getQuestions().stream().map(q ->
                                                                QuestionResponse.builder()
                                                                        .id(q.getId())
                                                                        .type(q.getType())
                                                                        .question(q.getQuestion())
                                                                        .questionImg(q.getQuestionImg())
                                                                        .description(q.getDescription())
                                                                        .questionOrder(q.getQuestionOrder())
                                                                        .answer(q.getAnswer() != null ?
                                                                                AnswerResponse.builder()
                                                                                        .id(q.getAnswer().getId())
                                                                                        .choiceA(q.getAnswer().getChoiceA())
                                                                                        .choiceB(q.getAnswer().getChoiceB())
                                                                                        .choiceC(q.getAnswer().getChoiceC())
                                                                                        .choiceD(q.getAnswer().getChoiceD())
                                                                                        .choiceAImg(q.getAnswer().getChoiceAImg())
                                                                                        .choiceBImg(q.getAnswer().getChoiceBImg())
                                                                                        .choiceCImg(q.getAnswer().getChoiceCImg())
                                                                                        .choiceDImg(q.getAnswer().getChoiceDImg())
                                                                                        .correctAnswer(q.getAnswer().getCorrectAnswer())
                                                                                        .build() : null)
                                                                        .build())
                                                        .collect(Collectors.toList())
                                        )
                                        .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }
}
