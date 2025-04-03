package com.example.demo.service;

import com.example.demo.dto.request.CourseRequest;
import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class CourseService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_COURSES_KEY = "all_courses"; // Key Redis

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {

//        return courseRepository.findAll();
        // Kiểm tra xem danh sách Course có trong Redis không
        List<Course> courses = (List<Course>) redisTemplate.opsForValue().get(REDIS_COURSES_KEY);

        if (courses == null) {
            // Nếu không có trong Redis, lấy từ Database
            courses = courseRepository.findAll();

            // Lưu vào Redis với thời gian 30 phút
            redisTemplate.opsForValue().set(REDIS_COURSES_KEY, courses, 30, TimeUnit.MINUTES);
        }

        return courses;
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Course updateCourse(Long id, CourseRequest courseRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Optional.ofNullable(courseRequest.getTitle()).ifPresent(course::setTitle);
        Optional.ofNullable(courseRequest.getImageSrc()).ifPresent(course::setImageSrc);


        return courseRepository.save(course);
    }
}