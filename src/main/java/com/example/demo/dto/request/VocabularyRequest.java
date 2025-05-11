package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyRequest {
    private String Eng;
    private String Vie;
    private String note;
    private int orderVocabulary;
}
