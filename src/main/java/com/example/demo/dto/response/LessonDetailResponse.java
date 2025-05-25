package com.example.demo.dto.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDetailResponse {
    private List<ChallengeDTO> challenges;
    private List<VocabularyResponse> vocabularies;
}

