package com.example.demo.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOptionRequestDto {
    private String audioSrc;
    private boolean correct;
    private String imgSrc;
    private String answers;
    private boolean deleteFlag;
}