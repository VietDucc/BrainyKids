package com.example.demo.dto.request;

import com.example.demo.enums.ChallengeType;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;

@Getter
@Setter
public class ChallengesRequest {
    private ChallengeType type;
    private String question;
    private int orderChallenge;
}
