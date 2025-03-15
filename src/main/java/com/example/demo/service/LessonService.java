package com.example.demo.service;

import com.example.demo.dto.request.ChallengeRequest;
import com.example.demo.dto.request.LessonRequest;
import com.example.demo.entity.Challenge;
import com.example.demo.entity.Lesson;
import com.example.demo.entity.Unit;
import com.example.demo.repository.ChallengeRepository;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UnitRepository unitRepository;

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
        return lessonRepository.save(lesson);
    }

    /**
     * getChallengeByLessonId
     * @param lessonId
     * @return
     */
    public List<Challenge> getChallengeByLessonId(Long lessonId) {
        return challengeRepository.findByLesson_Id(lessonId);
    }

}
