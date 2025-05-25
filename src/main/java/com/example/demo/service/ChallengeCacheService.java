package com.example.demo.service;

import com.example.demo.entity.Challenge;
import com.example.demo.repository.ChallengeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
    @CachePut(value = "challengeByLesson", key = "#lessonId")
    public List<Challenge> refreshChallengeByLessonId(Long lessonId) {
        System.out.println("Refreshing cache from DB for lessonId = " + lessonId);
        return challengeRepository.findByLessonIdWithOptions(lessonId);
    }

    @CacheEvict(value = "challengeByLesson", key = "#lessonId")
    public void evictChallengeCache(Long lessonId) {
        System.out.println("Evicting cache for lessonId = " + lessonId);
    }
}
