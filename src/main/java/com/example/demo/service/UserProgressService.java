package com.example.demo.service;

import com.example.demo.entity.UserProgress;
import com.example.demo.repository.LessonProgressRepository;
import com.example.demo.repository.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProgressService {
    private final UserProgressRepository userProgressRepository;
    private final LessonProgressRepository lessonProgressRepository;

    public void updateUserProgress(String clerkUserId) {
        LocalDate today = LocalDate.now();

        // 🔥 Lấy ngày gần nhất hoàn thành từ bảng lesson_progress
        Optional<Date> lastCompletedOpt = lessonProgressRepository.findLastCompletedDate(clerkUserId);
        LocalDate lastCompletedDate = lastCompletedOpt.map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).orElse(null);

        UserProgress userProgress = userProgressRepository.findByClerkUserId(clerkUserId)
                .orElseGet(() -> new UserProgress(null, clerkUserId, 0, null));

        if (lastCompletedDate == null || ChronoUnit.DAYS.between(lastCompletedDate, today) > 1) {
            // Nếu bỏ lỡ một ngày, reset streak về 1
            userProgress.setStreakCount(1);
        } else if (!lastCompletedDate.isEqual(today)) {
            // Nếu là ngày mới và streak liên tục, tăng streak
            userProgress.setStreakCount(userProgress.getStreakCount() + 1);
        }

        userProgress.setLastCompletedDate(lastCompletedDate);
        userProgressRepository.save(userProgress);
    }

    public int getUserStreak(String clerkUserId) {
        LocalDate today = LocalDate.now();

        // 🔥 Lấy ngày gần nhất hoàn thành từ bảng lesson_progress
        Optional<Date> lastCompletedOpt = lessonProgressRepository.findLastCompletedDate(clerkUserId);
        LocalDate lastCompletedDate = lastCompletedOpt.map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).orElse(null);

        UserProgress userProgress = userProgressRepository.findByClerkUserId(clerkUserId)
                .orElseGet(() -> new UserProgress(null, clerkUserId, 0, null));

        if (lastCompletedDate == null || ChronoUnit.DAYS.between(lastCompletedDate, today) > 1) {
            userProgress.setStreakCount(0);
            userProgressRepository.save(userProgress);
        }

        return userProgress.getStreakCount();
    }
}
