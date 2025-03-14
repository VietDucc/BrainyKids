package com.example.demo.service;

import com.example.demo.entity.Unit;
import com.example.demo.entity.Course;
import com.example.demo.repository.UnitRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.dto.request.UnitRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // Tạo Unit mớ i trong một Course
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

    //PUT
    public Unit updateUnit(Long unitId, UnitRequest unitRequest) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        Optional.ofNullable(unitRequest.getTitle()).ifPresent(unit::setTitle);
        Optional.ofNullable(unitRequest.getDescription()).ifPresent(unit::setDescription);
        Optional.ofNullable(unitRequest.getOrderUnit()).ifPresent(unit::setOrderUnit);

        return unitRepository.save(unit);
    }


    //DELETE
    public void deleteUnit(Long unitId) {
        if(!unitRepository.existsById(unitId)) {
            throw new RuntimeException("Unit not found");
        } else {
            unitRepository.deleteById(unitId);
        }
    }
}
