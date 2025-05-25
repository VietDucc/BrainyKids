package com.example.demo.service;

import com.example.demo.entity.Challenge;
import com.example.demo.repository.ChallengeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChallengeCacheService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "challengeByLesson", key = "#lessonId")
    public List<Challenge> getChallengeByLessonId(Long lessonId) {
        System.out.println("Loading from DB for lessonId = " + lessonId);


        // gọi method có fetch join để dữ liệu lazy được load đầy đủ
        return challengeRepository.findByLessonIdWithOptions(lessonId);
    }
}
