package com.example.demo.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeOptionDTO {
    private Long id;
    private String textOption;
    private boolean correct;
    private String imageSrc;
    private String audioSrc;
}
