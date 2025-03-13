package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengesOptionRequest {
    private String text;
    private boolean correct;
    private String imageSrc;
    private String audioSrc;
}
