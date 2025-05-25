package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeProgressRequest {
    private String userId;
    private boolean completed;
}
