package com.example.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChallengeOptionRequestDto {
    private long id;

    private String audioSrc;

    @NotEmpty
    private boolean correctPoint;

    private String image_src;

    private String textOption;

    private long challangeId;

    private boolean deleteFlag;
}
