package com.example.demo.service;

import com.example.demo.dto.request.LessonRequest;
import com.example.demo.dto.response.ChallengeDTO;
import com.example.demo.dto.response.LessonDetailResponse;
import com.example.demo.dto.response.VocabularyResponse;
import com.example.demo.entity.Challenge;
import com.example.demo.entity.Lesson;
import com.example.demo.entity.Unit;
import com.example.demo.entity.Vocabulary;
import com.example.demo.mapper.ChallengeMapper;
import com.example.demo.mapper.VocabularyMapper;
import com.example.demo.repository.ChallengeRepository;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.UnitRepository;
import com.example.demo.repository.VocabularyRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private ChallengeCacheService challengeCacheService;

    public List<Lesson> getLessonByUnitId(Long unitId) {
        return lessonRepository.findByUnit_Id(unitId);
    }

    public Lesson createLesson(Long unitId, LessonRequest lessonRequest) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonRequest.getTitle());
        lesson.setOrderIndex(lessonRequest.getOrderIndex());
        lesson.setUnit(unit);
        lesson.setDifficulty(lessonRequest.getDifficulty());
        return lessonRepository.save(lesson);
    }

    @CacheEvict(value= "challengesByLesson", key="#lessonId")
    public Lesson updateLesson(Long lessonId, LessonRequest lessonRequest) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        Optional.ofNullable(lessonRequest.getTitle()).ifPresent(lesson::setTitle);
        Optional.ofNullable(lessonRequest.getOrderIndex()).ifPresent(lesson::setOrderIndex);
        Optional.ofNullable(lessonRequest.getDifficulty()).ifPresent(lesson::setDifficulty);

        return lessonRepository.save(lesson);
    }

    // Xóa bài học
    public void deleteLesson(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new RuntimeException("Lesson not found");
        }
        lessonRepository.deleteById(lessonId);
    }
    /**
     * getChallengeByLessonId
     * @param lessonId
     * @return
     */
    @Transactional(readOnly = true)
    public LessonDetailResponse getLessonDetailByLessonId(Long lessonId) {
        List<Challenge> challenges = challengeCacheService.getChallengeByLessonId(lessonId);
        List<ChallengeDTO> challengeDTOs = challenges.stream()
                .map(ChallengeMapper::toDTO)
                .collect(Collectors.toList());

        List<Vocabulary> vocabularies = vocabularyRepository.findByLesson_id(lessonId);
        List<VocabularyResponse> vocabularyResponses = vocabularies.stream()
                .map(VocabularyMapper::toResponse)
                .collect(Collectors.toList());

        return LessonDetailResponse.builder()
                .challenges(challengeDTOs)
                .vocabularies(vocabularyResponses)
                .build();
    }

    //Load va cache 1 lesson + 10 lesson tiep theo
    public void cacheLessonAdnNext10(Long startLessonId){
        for (long i = startLessonId; i < startLessonId + 5; i++) {
            challengeCacheService.getChallengeByLessonId(i);
        }
    }
}

