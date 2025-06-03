package com.example.demo.service;

import com.example.demo.dto.response.UserScoreResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderBoardService {

    @Autowired
    private UserRepository userRepository;

    public List<UserScoreResponseDTO> getAllUserScores() {
        List<User> users = userRepository.findTop10ByOrderByScoreDesc();
        return users.stream()
                .map(user -> new UserScoreResponseDTO(
                        user.getId(),
                        user.getClerkUserId(),
                        user.getFirstName() + " " + user.getLastName(),
                        user.getProfile_image_url(),
                        user.getScore()
                ))
                .collect(Collectors.toList());
                .collect(Collectors.toList());
    }
}