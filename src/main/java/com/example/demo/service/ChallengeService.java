package com.example.demo.service;

import com.example.demo.dto.request.ChallengeRequest;
import com.example.demo.entity.Challenge;
import com.example.demo.entity.ChallengeOption;
import com.example.demo.entity.Lesson;
import com.example.demo.repository.ChallengeOptionRepository;
import com.example.demo.repository.ChallengeRepository;
import com.example.demo.repository.LessonRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
