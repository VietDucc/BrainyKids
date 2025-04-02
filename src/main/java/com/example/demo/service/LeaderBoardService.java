package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeaderBoardService {

    @Autowired
    private final UserRepository userRepository;

    public LeaderBoardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getTop100Users() {
        return userRepository.findTop10OderedByScoreDesc();
    }
}
