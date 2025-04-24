package com.example.demo.service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setClerkUserId(userRequest.getData().getId());
        user.setFirstName(userRequest.getData().getFirst_name());
        user.setLastName(userRequest.getData().getLast_name());
        user.setProfile_image_url(userRequest.getData().getProfile_image_url());
        user.setScore(0);
        user.setNewUser(true);
        // Lấy email đầu tiên (nếu có)
        if (userRequest.getData().getEmail_addresses() != null && !userRequest.getData().getEmail_addresses().isEmpty()) {
            user.setEmail(userRequest.getData().getEmail_addresses().get(0).getEmail_address());
        }

        return userRepository.save(user);
    }

    public User updateUserScore(String clerkUserId, int newScore) {
        User user = userRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setScore(newScore);
        return userRepository.save(user);
    }

}
