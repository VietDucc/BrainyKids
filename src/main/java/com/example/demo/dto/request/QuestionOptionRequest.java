package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionOptionRequest {
    private String answers;
    private boolean correct;
    private String imgSrc;
    private String audioSrc;
}
