package com.example.demo.service;

import com.example.demo.entity.Unit;
import com.example.demo.entity.Course;
import com.example.demo.repository.UnitRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.dto.request.UnitRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    private final UnitRepository unitRepository;
    private final CourseRepository courseRepository;

    // ✅ Sử dụng constructor injection thay vì @Autowired trên field
    public UnitService(UnitRepository unitRepository, CourseRepository courseRepository) {
        this.unitRepository = unitRepository;
        this.courseRepository = courseRepository;
    }

    // Lấy danh sách Unit theo Course ID
    public List<Unit> getUnitsByCourseId(Long courseId) {
        return unitRepository.findByCourseId(courseId);
    }

    // Tạo Unit mới trong một Course
    public Unit createUnit(Long courseId, UnitRequest unitRequest) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Unit unit = new Unit();
        unit.setTitle(unitRequest.getTitle());
        unit.setDescription(unitRequest.getDescription());
        unit.setOrderUnit(unitRequest.getOrderUnit()); // ✅ Đảm bảo đúng tên trường trong Entity
        unit.setCourse(course);

        return unitRepository.save(unit);
    }
}
