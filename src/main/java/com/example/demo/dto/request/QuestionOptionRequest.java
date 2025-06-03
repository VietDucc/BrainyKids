package com.example.demo.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOptionRequest {
    private String answers;
    private boolean correct;
    private String imgSrc;
    private String audioSrc;
}
