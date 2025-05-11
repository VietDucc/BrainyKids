package com.example.demo.service;

import com.example.demo.entity.Lesson;
import com.example.demo.entity.Vocabulary;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VocabularyService {
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public List<Vocabulary> getVocabularyByLessonId(long lesson_id) {
        return vocabularyRepository.findByLesson_id(lesson_id);
    }

    public Vocabulary createVocabulary(Long lessonId, Vocabulary request) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        Vocabulary vocabulary = Vocabulary.builder()
                .Eng(request.getEng())
                .Vie(request.getVie())
                .note(request.getNote())
                .orderVocabulary(request.getOrderVocabulary())
                .lesson(lesson)
                .build();

        return vocabularyRepository.save(vocabulary);
    }

    public void deleteVocabulary(Long vocabulary_id) {
        vocabularyRepository.deleteById(vocabulary_id);
    }

    public Optional<Vocabulary> getVocabularyById(Long vocabulary_id) {
        return vocabularyRepository.findById(vocabulary_id);
    }
}
