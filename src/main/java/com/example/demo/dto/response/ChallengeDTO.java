package com.example.demo.dto.response;


import com.example.demo.enums.ChallengeType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeDTO {
    private Long id;
    private ChallengeType type;
    private String imgSrc;
    private String question;
    private int orderChallenge;
    private List<ChallengeOptionDTO> challengesOption;
    //private List<ChallengeProgressDTO> challengesProgress;
}

