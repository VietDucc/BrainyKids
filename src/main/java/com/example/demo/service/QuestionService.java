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
        Question question = Question.builder()
                .type(questionRequest.getType())
                .question(questionRequest.getQuestion())
                .description(questionRequest.getDescription())
                .imgSrc(questionRequest.getImgSrc())
                .questionOrder(questionRequest.getQuestionOrder())
                .part(partRepository.findById(questionRequest.getPartId())
                        .orElseThrow(() -> new RuntimeException("Part not found")))
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

                // Cột 0: partId
                Cell partIdCell = row.getCell(0);
                if (partIdCell == null || partIdCell.getCellType() != CellType.NUMERIC) continue;
                long partId = (long) partIdCell.getNumericCellValue();
                updatedPartIds.add(partId);

                // Cột 1: type
                String typeStr = getCellString(row.getCell(1));
                QuestionType type = mapToQuestionType(typeStr); // chỉ cho part1,2,3,5,6

                // Cột 2: question
                String question = getCellString(row.getCell(2));
                // Cột 3: description
                String description = getCellString(row.getCell(3));

                // Cột 4-7: các phương án trả lời
                List<String> answers = new ArrayList<>();
                for (int col = 4; col <= 7; col++) {
                    String answer = getCellString(row.getCell(col));
                    if (answer != null && !answer.isEmpty()) {
                        answers.add(answer.trim());
                    }
                }

                // Cột 8: đáp án đúng (A, B, C, D)
                String correctAnswer = getCellString(row.getCell(8));

                // Cột 9: ảnh
                String image = getCellString(row.getCell(9));

                // Tạo builder cho câu hỏi
                QuestionRequest.QuestionRequestBuilder builder = QuestionRequest.builder()
                        .partId(partId)
                        .question(question)
                        .description(description)
                        .imgSrc(image)
                        .type(type)
                        .questionOrder(i - 1);

                // Tạo danh sách phương án
                List<QuestionOptionRequestDto> options = new ArrayList<>();
                for (int j = 0; j < answers.size(); j++) {
                    char label = (char) ('A' + j);
                    options.add(QuestionOptionRequestDto.builder()
                            .answers(answers.get(j))
                            .correct(String.valueOf(label).equalsIgnoreCase(correctAnswer))
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

    // Hỗ trợ đọc String từ ô bất kỳ
    private String getCellString(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> null;
        };
    }

    // Chỉ chấp nhận các loại part hợp lệ: 1, 2, 3, 5, 6
    private QuestionType mapToQuestionType(String text) {
        if (text == null) throw new IllegalArgumentException("Question type cannot be null");

        switch (text.trim().toLowerCase()) {
            case "part1": return QuestionType.part1;
            case "part2": return QuestionType.part2;
            case "part3": return QuestionType.part3;
            case "part5": return QuestionType.part5;
            case "part6": return QuestionType.part6;
            default:
                throw new IllegalArgumentException("Unsupported question type: " + text +
                        ". Only part1, part2, part3, part5, part6 are allowed.");
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
                    .correct(option.isCorrect())
                    .imgSrc(option.getImgSrc())
                    .question(question)
                    .build();
            questionOptions.add(questionOption);
        });
        return questionOptions;
    }
}