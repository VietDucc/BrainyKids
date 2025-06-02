package com.example.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionOptionRequestDto {
    private String audioSrc;

    @NotEmpty
    private boolean correctPoint;

    private String imgSrc;
    private String answers;
    private boolean deleteFlag;
}