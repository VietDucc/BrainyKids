package com.example.demo.mapper;

import com.example.demo.dto.response.VocabularyResponse;
import com.example.demo.entity.Vocabulary;

public class VocabularyMapper {

    public static VocabularyResponse toResponse(Vocabulary vocabulary) {
        if (vocabulary == null) return null;

        return new VocabularyResponse(
                vocabulary.getId(),
                vocabulary.getEng(),
                vocabulary.getVie(),
                vocabulary.getNote(),
                vocabulary.getOrderVocabulary()
        );
    }
}
