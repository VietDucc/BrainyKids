package com.example.demo.service;

import com.example.demo.dto.request.ChallengeOptionRequestDto;
import com.example.demo.dto.request.ChallengeRequest;
import com.example.demo.entity.Challenge;
import com.example.demo.entity.ChallengeOption;
import com.example.demo.enums.ChallengeType;
import com.example.demo.repository.ChallengeOptionRepository;
import com.example.demo.repository.ChallengeRepository;
import com.example.demo.repository.LessonRepository;
import lombok.Builder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Builder
public class ChallengeService {
    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private LessonRepository lessonRepository;

    private ChallengeOptionRepository challengeOptionRepository;

    public List<Challenge> getChallengeByLessonId(Long lessonId) {
        return challengeRepository.findByLesson_Id(lessonId);
    }

    /**
     * createChallenge
     * @param challengesRequest
     * @return
     */
    public long createChallenge( ChallengeRequest challengesRequest) {
        long cntChallengeId = challengeRepository.findMaxId() + 1;
        Challenge challenge = Challenge
        .builder()
        .type(challengesRequest.getType())
        .imgSrc(challengesRequest.getImgSrc())
        .question(challengesRequest.getQuestion())
        .orderChallenge(challengesRequest.getOrderChallenge())
        .lesson( lessonRepository.findById(challengesRequest.getLessonId())
                .orElseThrow(() -> new RuntimeException("LessonId isn't correct!")))
        .id(cntChallengeId)
        .build();

        challenge.setChallengesOption(buildChallengeOptions(challenge, challengesRequest));

        challengeRepository.save(challenge);
        return challenge.getId();
    }

    public void importChallengesFromExcel(MultipartFile file) {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                long lessonId = (long) row.getCell(0).getNumericCellValue();
                String typeStr = row.getCell(1).getStringCellValue();
                String question = row.getCell(2).getStringCellValue();

                // Đọc đáp án linh hoạt
                List<String> answers = new ArrayList<>();
                for (int col = 3; col <= 6; col++) {
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
                Cell correctCell = row.getCell(7);
                if (correctCell != null && correctCell.getCellType() == CellType.STRING) {
                    correctAnswer = correctCell.getStringCellValue().trim();
                }

                String image = row.getCell(8) != null ? row.getCell(8).getStringCellValue() : null;
                String audio = row.getCell(9) != null ? row.getCell(9).getStringCellValue() : null;

                ChallengeRequest.ChallengeRequestBuilder builder = ChallengeRequest.builder()
                        .lessonId(lessonId)
                        .question(question)
                        .imgSrc(image)
                        .type(mapToType(typeStr))
                        .orderChallenge(i - 1);

                List<ChallengeOptionRequestDto> options = new ArrayList<>();
                for (int j = 0; j < answers.size(); j++) {
                    char label = (char) ('A' + j);
                    options.add(ChallengeOptionRequestDto.builder()
                            .textOption(answers.get(j))
                            .correctPoint(String.valueOf(label).equalsIgnoreCase(correctAnswer))
                            .image_src(null)
                            .audioSrc(null)
                            .deleteFlag(false)
                            .build());
                }

                builder.challengeOptions(options);
                createChallenge(builder.build());
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to import challenges from Excel", e);
        }
    }


    private ChallengeType mapToType(String text) {
        switch (text.trim().toLowerCase()) {
            case "select": return ChallengeType.SELECT;
            case "image": return ChallengeType.SINGLE;
            case "arrange": return ChallengeType.MULTI;
            case "fill": return ChallengeType.SELECT; // or another if defined
            default: throw new IllegalArgumentException("Unknown challenge type: " + text);
        }
    }


    /**
     * updateChallenge
     * @param challengeId
     * @param challengeRequest
     * @return
     */
    public long updateChallenge(Long challengeId, ChallengeRequest challengeRequest) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

//        challenge.setType(challengeRequest.getType());
//        challenge.setQuestion(challengeRequest.getQuestion());
//        challenge.setOrderChallenge(challengeRequest.getOrderChallenge());
//        if(challenge.getChallengesOption() != null)
//        {
//            challengeOptionRepository.deleteAll(challenge.getChallengesOption());
//        }
//        challenge.setChallengesOption(buildChallengeOptions(challenge, challengeRequest));
//
//        challengeRepository.save(challenge);
//
//        return challenge.getId();

        deleteChallenge(challengeId);
        return createChallenge(challengeRequest);
    }

    /**
     * deleteChallenge
     * @param challengeId
     */
    public long deleteChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        challengeOptionRepository.deleteAll(challenge.getChallengesOption());
        challengeRepository.delete(challenge);
        return challenge.getId();
    }

   // -- PRIVATE ZONE --

    /**
     * buildChallengeOptions
     * @param challenge
     * @param challengesRequest
     * @return
     */
    private List<ChallengeOption> buildChallengeOptions(Challenge challenge, ChallengeRequest challengesRequest)
    {
        List<ChallengeOption> challengeOptions = new ArrayList<>();
        challengesRequest.getChallengeOptions().forEach(x -> {
            ChallengeOption challengeOption =
                                    ChallengeOption
                    .builder()
                    .textOption(x.getTextOption())
                    .audioSrc(x.getAudioSrc())
                    .correct(x.isCorrectPoint())
                    .imageSrc(x.getImage_src())
                    .challenge(challenge)
                    .build();
            challengeOptions.add(challengeOption);
        });
        return challengeOptions;
    }
}
