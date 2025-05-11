package com.example.demo.controller;


import com.example.demo.entity.Vocabulary;
import com.example.demo.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vocabulary")
public class VocabularyController {

    @Autowired
    private VocabularyService vocabularyService;

    @GetMapping("/lesson/{lessonId}")
    public List<Vocabulary> getVocabularyByLessonId(@PathVariable Long lessonId){
        return vocabularyService.getVocabularyByLessonId(lessonId);
    }

    @PostMapping("/lesson/{lessonId}")
    public ResponseEntity<Vocabulary> createVocabulary(@PathVariable Long lessonId, @RequestBody Vocabulary request){
        return ResponseEntity.ok(vocabularyService.createVocabulary(lessonId, request));
    }

    @DeleteMapping("/{vocabId}")
    public ResponseEntity<Void> deleteVocabulary(@PathVariable Long vocabId){
        vocabularyService.deleteVocabulary(vocabId);
        return ResponseEntity.noContent().build();
    }
}
