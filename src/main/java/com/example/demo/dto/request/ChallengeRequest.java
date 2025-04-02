package com.example.demo.dto.request;

import com.example.demo.entity.ChallengeOption;
import com.example.demo.entity.ChallengeProgress;
import com.example.demo.enums.ChallengeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ChallengeRequest {
    private ChallengeType type;
    private String question;
    private int orderChallenge;
    private long lessonId;
    private  List<ChallengeOptionRequestDto> challengeOptions = new ArrayList<>();
}
