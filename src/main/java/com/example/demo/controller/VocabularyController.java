package com.example.demo.controller;


import com.example.demo.entity.Vocabulary;
import com.example.demo.service.VocabularyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.request.VocabularyRequest;
import org.springframework.web.multipart.MultipartFile;

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

//    @PostMapping("/lesson/{lessonId}")
//    public ResponseEntity<Vocabulary> createVocabulary(@PathVariable Long lessonId, @RequestBody Vocabulary request){
//        return ResponseEntity.ok(vocabularyService.createVocabulary(lessonId, request));
//    }

    @PostMapping("/batch")
    public ResponseEntity<?> createMultiple(@RequestBody List<VocabularyRequest> requests) {
        for (int i = 0; i < requests.size(); i++) {
            VocabularyRequest req = requests.get(i);
            req.setOrderVocabulary(i);
            vocabularyService.createVocabulary(req);
        }
        return ResponseEntity.ok("Created all vocabulary entries.");
    }

    @PostMapping
    public Long createVocabulary(@RequestBody VocabularyRequest request) {
        return vocabularyService.createVocabulary(request);
    }

    @Operation(summary = "Import challenges from Excel file")
    @PostMapping(value = "/challenge/multi", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importChallenges(
            @RequestParam("file") MultipartFile file
    ) {
        vocabularyService.importVocabulariesFromExcel(file);
        return ResponseEntity.ok("Import successful");
    }

    @DeleteMapping("/{vocabId}")
    public ResponseEntity<Void> deleteVocabulary(@PathVariable Long vocabId){
        vocabularyService.deleteVocabulary(vocabId);
        return ResponseEntity.noContent().build();
    }
}
