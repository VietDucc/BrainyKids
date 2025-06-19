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

    private static final String COURSE_KEY = "course:";
//    public List<Course> getAllCourses() {
//
//        return courseRepository.findAll();
//
//    }
public List<Course> getAllCourses() {
    List<Course> courses = (List<Course>) redisTemplate.opsForValue().get(COURSE_KEY);
    System.out.println("Dữ liệu trong Redis: " + courses); // Kiểm tra dữ liệu trong Redis

    if (courses == null) {
        System.out.println("Không có dữ liệu trong Redis, lấy từ PostgreSQL");
        // Lấy dữ liệu từ PostgreSQL
         courses = courseRepository.findAll();
        redisTemplate.opsForValue().set(COURSE_KEY, courses); // Lưu dữ liệu vào Redis
    }

    return courses;
}

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course addCourse(Course course) {
        // Thêm course vào cơ sở dữ liệu
        Course savedCourse = courseRepository.save(course);

        // Xóa dữ liệu cũ trong Redis

        // Lưu lại danh sách khóa học mới vào Redis
        List<Course> courses = courseRepository.findAll();
        redisTemplate.opsForValue().set(COURSE_KEY, courses, 1, TimeUnit.HOURS);

        return savedCourse;
    }


    public void deleteCourse(Long id) {

        courseRepository.deleteById(id);
        // Cap nhat lai Redis sau khi xoa
        List<Course> courses = courseRepository.findAll();
        redisTemplate.opsForValue().set(COURSE_KEY, courses, 1, TimeUnit.HOURS);


    }

    public Course updateCourse(Long id, CourseRequest courseRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Optional.ofNullable(courseRequest.getTitle()).ifPresent(course::setTitle);
        Optional.ofNullable(courseRequest.getImageSrc()).ifPresent(course::setImageSrc);

        // Cập nhật lại Redis
        List<Course> courses = courseRepository.findAll();
        redisTemplate.opsForValue().set(COURSE_KEY, courses, 1, TimeUnit.HOURS);

        return courseRepository.save(course);
    }
}