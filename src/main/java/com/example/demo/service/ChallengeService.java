package com.example.demo.service;

import com.example.demo.dto.request.ChallengeRequest;
import com.example.demo.entity.Challenge;
import com.example.demo.entity.Lesson;
import com.example.demo.repository.ChallengeRepository;
import com.example.demo.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeService {
    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public List<Challenge> getChallengeByLessonId(Long lessonId) {
        return challengeRepository.findByLesson_Id(lessonId);
    }

    public Challenge createChallenge(Long lessonId, ChallengeRequest challengesRequest) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        Challenge challenge = new Challenge();
        challenge.setType(challengesRequest.getType());
        challenge.setQuestion(challengesRequest.getQuestion());
        challenge.setOrderChallenge(challengesRequest.getOrderChallenge());
        challenge.setLesson(lesson);
        return challengeRepository.save(challenge);
    }

    public Challenge updateChallenge(Long challengeId, ChallengeRequest challengeRequest) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));
        challenge.setType(challengeRequest.getType());
        challenge.setQuestion(challengeRequest.getQuestion());
        challenge.setOrderChallenge(challengeRequest.getOrderChallenge());

        return challengeRepository.save(challenge);
    }

    public void deleteChallenge(Long challengeId) {
        challengeRepository.deleteById(challengeId);
    }
}
