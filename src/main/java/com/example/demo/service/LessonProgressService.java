package com.example.demo.service;

import com.example.demo.dto.response.LessonProgressResponse;
import com.example.demo.entity.Lesson;
import com.example.demo.entity.LessonProgress;
import com.example.demo.entity.User;
import com.example.demo.repository.LessonProgressRepository;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonProgressService {
    @Autowired
    private LessonProgressRepository lessonProgressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public LessonProgress updateLessonProgress(String clerkUserId, long lessonId, boolean completed) {
        // Tìm user theo clerkUserId
        User user = userRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tìm lesson theo lessonId
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        // Tìm danh sách lesson progress
        List<LessonProgress> progressList = lessonProgressRepository.findByUser_ClerkUserIdAndLesson_Id(clerkUserId, lessonId);

        LessonProgress progress;
        if (!progressList.isEmpty()) {
            // Nếu đã tồn tại, cập nhật trạng thái completed
            progress = progressList.get(0);
            progress.setCompleted(completed);
        } else {
            // Nếu chưa có, tạo mới
            progress = new LessonProgress();
            progress.setUser(user);
            progress.setLesson(lesson);
            progress.setCompleted(completed);
        }

        return lessonProgressRepository.save(progress);
    }


    public List<LessonProgressResponse> getLessonProgressByUserId(String clerkUserId) {
        List<LessonProgress> progressList = lessonProgressRepository.findByUser_ClerkUserId(clerkUserId);

        return progressList.stream()
                .map(progress -> new LessonProgressResponse(
                        progress.getId(),
                        progress.isCompleted(),
                        progress.getLesson().getId(), // Lấy ID của bài học
                        progress.getUser().getClerkUserId() // Lấy ID của user
                ))
                .collect(Collectors.toList());
    }
}
