package com.example.demo.service;

import com.example.demo.dto.request.ChallengeOptionRequest;
import com.example.demo.entity.Challenge;
import com.example.demo.entity.ChallengeOption;
import com.example.demo.repository.ChallengeOptionRepository;
import com.example.demo.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public ChallengeOption updateChallengeOption(Long optionId, ChallengeOptionRequest challengeOptionRequest) {
        ChallengeOption challengeOption = challengeOptionRepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("Challenge Option not found"));

        Optional.ofNullable(challengeOptionRequest.getText()).ifPresent(challengeOption::setTextOption);
        Optional.ofNullable(challengeOptionRequest.isCorrect()).ifPresent(challengeOption::setCorrect);
        Optional.ofNullable(challengeOptionRequest.getImageSrc()).ifPresent(challengeOption::setImageSrc);
        Optional.ofNullable(challengeOptionRequest.getAudioSrc()).ifPresent(challengeOption::setAudioSrc);

        return challengeOptionRepository.save(challengeOption);
    }


    public void deleteChallengeOption(Long optionId) {
        if (!challengeOptionRepository.existsById(optionId)) {
            throw new RuntimeException("Challenge Option not found");
        }
        challengeOptionRepository.deleteById(optionId);
    }

}
