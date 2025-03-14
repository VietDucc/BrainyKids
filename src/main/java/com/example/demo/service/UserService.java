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
        user.setClerkUserId(userRequest.getId());
        user.setFirstName(userRequest.getFirst_name());
        user.setLastName(userRequest.getLast_name());
       return  userRepository.save(user);

    }

}
