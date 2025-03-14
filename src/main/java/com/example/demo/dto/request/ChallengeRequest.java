package com.example.demo.dto.request;

import com.example.demo.enums.ChallengeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeRequest {
    private ChallengeType type;
    private String question;
    private int orderChallenge;
}
