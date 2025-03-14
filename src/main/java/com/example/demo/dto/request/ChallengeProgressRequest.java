package com.example.demo.dto.request;

import com.example.demo.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeProgressRequest {
    private String userId;
    private boolean completed;
}
