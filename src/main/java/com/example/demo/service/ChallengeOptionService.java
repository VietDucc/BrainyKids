package com.example.demo.service;

import com.example.demo.dto.request.ChallengeOptionRequest;
import com.example.demo.entity.Challenge;
import com.example.demo.entity.ChallengeOption;
import com.example.demo.repository.ChallengeOptionRepository;
import com.example.demo.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeOptionService {
    @Autowired
    private ChallengeOptionRepository challengeOptionRepository;

    @Autowired
    private ChallengeRepository challengeRepository; // Thêm để kiểm tra challengeId

    public List<ChallengeOption> getChallengeOptionsByChallengeId(Long challengeId) {
        return challengeOptionRepository.findByChallenge_Id(challengeId);
    }

    public ChallengeOption createChallengeOption(Long challengeId, ChallengeOptionRequest challengeOptionRequest) {
        // Kiểm tra challengeId có tồn tại không
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        // Tạo đối tượng ChallengeOption từ DTO
        ChallengeOption challengeOption = new ChallengeOption();
        challengeOption.setTextOption(challengeOptionRequest.getText());
        challengeOption.setCorrect(challengeOptionRequest.isCorrect());
        challengeOption.setImageSrc(challengeOptionRequest.getImageSrc());
        challengeOption.setAudioSrc(challengeOptionRequest.getAudioSrc());
        challengeOption.setChallenge(challenge);

        return challengeOptionRepository.save(challengeOption);
    }


}
