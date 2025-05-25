package com.example.demo.mapper;


import com.example.demo.dto.response.ChallengeDTO;
import com.example.demo.dto.response.ChallengeOptionDTO;
import com.example.demo.entity.Challenge;

import java.util.stream.Collectors;

public class ChallengeMapper {
    public static ChallengeDTO toDTO(Challenge challenge) {
        return ChallengeDTO.builder()
                .id(challenge.getId())
                .type(challenge.getType())
                .imgSrc(challenge.getImgSrc())
                .question(challenge.getQuestion())
                .orderChallenge(challenge.getOrderChallenge())
                .challengesOption(
                        challenge.getChallengesOption().stream()
                                .map(option -> ChallengeOptionDTO.builder()
                                        .id(option.getId())
                                        .textOption(option.getTextOption())
                                        .correct(option.isCorrect())
                                        .imageSrc(option.getImageSrc())
                                        .audioSrc(option.getAudioSrc())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
