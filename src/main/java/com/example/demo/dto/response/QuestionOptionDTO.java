package com.example.demo.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionOptionDTO {
    private Long id;
    private String answers;
    private boolean correct;
    private String imgSrc;
    private String audioSrc;
}