package com.example.demo.service;

import com.example.demo.dto.request.QuestionOptionRequestDto;
import com.example.demo.dto.request.QuestionRequest;
import com.example.demo.entity.Part;
import com.example.demo.entity.Question;
import com.example.demo.entity.QuestionOption;
import com.example.demo.enums.QuestionType;
import com.example.demo.repository.PartRepository;
import com.example.demo.repository.QuestionOptionRepository;
import com.example.demo.repository.QuestionRepository;
import lombok.Builder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Builder
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @Autowired
    private QuestionCacheService questionCacheService;

    public List<Question> getQuestionsByPartId(Long partId) {
        return questionRepository.findByPart_Id(partId);
    }

    public long createQuestion(QuestionRequest questionRequest) {
        long questionId = questionRepository.findMaxId() + 1;
        Question question = Question.builder()
                .type(questionRequest.getType())
                .question(questionRequest.getQuestion())
                .description(questionRequest.getDescription())
                .imgSrc(questionRequest.getImgSrc())
                .questionOrder(questionRequest.getQuestionOrder())
                .part(partRepository.findById(questionRequest.getPartId())
                        .orElseThrow(() -> new RuntimeException("Part not found")))
                .id(questionId)
                .build();

        question.setQuestionOptions(buildQuestionOptions(question, questionRequest));
        questionRepository.save(question);
        questionCacheService.refreshQuestionsByPartId(questionRequest.getPartId());
        return question.getId();
    }

    public void importQuestionsFromExcel(MultipartFile file) {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Set<Long> updatedPartIds = new HashSet<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                long partId = (long) row.getCell(0).getNumericCellValue();
                updatedPartIds.add(partId);
                String typeStr = row.getCell(1).getStringCellValue();
                String question = row.getCell(2).getStringCellValue();
                String description = row.getCell(3).getStringCellValue();

                List<String> answers = new ArrayList<>();
                for (int col = 4; col <= 7; col++) {
                    Cell cell = row.getCell(col);
                    if (cell != null) {
                        String answer = null;
                        if (cell.getCellType() == CellType.STRING) {
                            answer = cell.getStringCellValue();
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            answer = String.valueOf(cell.getNumericCellValue());
                        }
                        if (answer != null && !answer.trim().isEmpty()) {
                            answers.add(answer.trim());
                        }
                    }
                }

                String correctAnswer = "";
                Cell correctCell = row.getCell(8);
                if (correctCell != null && correctCell.getCellType() == CellType.STRING) {
                    correctAnswer = correctCell.getStringCellValue().trim();
                }

                String image = row.getCell(9) != null ? row.getCell(9).getStringCellValue() : null;

                QuestionRequest.QuestionRequestBuilder builder = QuestionRequest.builder()
                        .partId(partId)
                        .question(question)
                        .description(description)
                        .imgSrc(image)
                        .type(mapToQuestionType(typeStr))
                        .questionOrder(i - 1);

                List<QuestionOptionRequestDto> options = new ArrayList<>();
                for (int j = 0; j < answers.size(); j++) {
                    char label = (char) ('A' + j);
                    options.add(QuestionOptionRequestDto.builder()
                            .answers(answers.get(j))
                            .correctPoint(String.valueOf(label).equalsIgnoreCase(correctAnswer))
                            .imgSrc(null)
                            .audioSrc(null)
                            .deleteFlag(false)
                            .build());
                }

                builder.questionOptions(options);
                createQuestion(builder.build());
            }

            updatedPartIds.forEach(questionCacheService::refreshQuestionsByPartId);

        } catch (IOException e) {
            throw new RuntimeException("Failed to import questions from Excel", e);
        }
    }

    private QuestionType mapToQuestionType(String text) {
        switch (text.trim().toLowerCase()) {
            case "exam1": return QuestionType.exam1;
            case "exam2": return QuestionType.exam2;
            case "exam3": return QuestionType.exam3;
            case "read1": return QuestionType.read1;
            case "read2": return QuestionType.read2;
            case "read3": return QuestionType.read3;
            case "read4": return QuestionType.read4;
            default: throw new IllegalArgumentException("Unknown question type: " + text);
        }
    }

    public long updateQuestion(Long questionId, QuestionRequest questionRequest) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        deleteQuestion(questionId);
        return createQuestion(questionRequest);
    }

    public long deleteQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Long partId = question.getPart().getId();
        questionOptionRepository.deleteAll(question.getQuestionOptions());
        questionRepository.delete(question);
        questionCacheService.refreshQuestionsByPartId(partId);
        return question.getId();
    }

    private List<QuestionOption> buildQuestionOptions(Question question, QuestionRequest questionRequest) {
        List<QuestionOption> questionOptions = new ArrayList<>();
        questionRequest.getQuestionOptions().forEach(option -> {
            QuestionOption questionOption = QuestionOption.builder()
                    .answers(option.getAnswers())
                    .audioSrc(option.getAudioSrc())
                    .correct(option.isCorrectPoint())
                    .imgSrc(option.getImgSrc())
                    .question(question)
                    .build();
            questionOptions.add(questionOption);
        });
        return questionOptions;
    }
}