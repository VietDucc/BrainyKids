package com.example.demo.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyRequest {
    private Long lessonId;
    private String Eng;
    private String Vie;
    private String note;
    private int orderVocabulary;
}
