package com.example.demo.service;

import com.example.demo.entity.Question;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionCacheService {
    @Autowired
    private QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "questionByPart", key = "#partId")
    public List<Question> getQuestionsByPartId(Long partId) {
        System.out.println("Loading from DB for partId = " + partId);
        return questionRepository.findByPartIdWithOptions(partId);
    }

    @CachePut(value = "questionByPart", key = "#partId")
    public List<Question> refreshQuestionsByPartId(Long partId) {
        System.out.println("Refreshing cache from DB for partId = " + partId);
        return questionRepository.findByPartIdWithOptions(partId);
    }

    @CacheEvict(value = "questionByPart", key = "#partId")
    public void evictQuestionCache(Long partId) {
        System.out.println("Evicting cache for partId = " + partId);
    }
}