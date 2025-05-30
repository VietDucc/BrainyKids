package com.example.demo.controller;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.request.UserScoreRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
// Controller: Nhan request va goi Service
public class UserController {
    @Autowired // Autowired: SP se tu dong inject mot instance cua UserService vao ma ko can
               // khoi tao thu
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/clerk")
    public User createUser(@RequestBody UserRequest userRequest) {
        System.out.println("Create User Clerk");
        return userService.createUser(userRequest);

    }
    @PutMapping("/{clerkUserId}/score")
   public User updateUserScore(@PathVariable String clerkUserId, @RequestBody UserScoreRequest userScoreRequest) {
        return userService.updateUserScore(clerkUserId, userScoreRequest.getScore());
    }

    @PutMapping("/{clerkUserId}/test-completed")
    public User updateUserAsTestCompleted(@PathVariable String clerkUserId) {
        User user = userRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setNewUser(false);
        return userRepository.save(user);
    }
    //Reset diem ve o vao ngay 1 hang thang luc 00:00
    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetMothlyScores() {
        List<User> users = userRepository.findAll();
        for(User user: users){
            user.setScore(0);

        }
        userRepository.saveAll(users);
        System.out.println("✅ Đã reset điểm về 0 cho tất cả user!");

    }

}
// Hanh trinh du lieu
// Khi nguoi dung gui request POST/ users
// 1. User gui du lieu JSON-> Spring Boot Controller nhan du lieu
// 2. Controller goi Service de xu ly logic
// 3. Service luu du lieu vao MySQL thong qua Repository
// 4. Tra ve ket qua JSON cho nguoi dung bang User

//UserCreationRequest dai dien cho kkieu du lieu dau vao
//Entity du lieu dau ra