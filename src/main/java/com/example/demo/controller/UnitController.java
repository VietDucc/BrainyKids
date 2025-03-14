package com.example.demo.controller;

import com.example.demo.dto.request.UnitRequest;
import com.example.demo.entity.Unit;
import com.example.demo.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController {
    @Autowired
    private UnitService unitService;

    @GetMapping("/{courseId}")
    public List<Unit> getUnitsByCourseId(@PathVariable Long courseId) {
        return unitService.getUnitsByCourseId(courseId);
    }

    @PostMapping("/{courseId}")
    public Unit createUnit(@PathVariable Long courseId, @RequestBody UnitRequest unitRequest) {
        return unitService.createUnit(courseId, unitRequest);
    }

    @PutMapping("/{unitId}")
    public Unit updateUnit(@PathVariable Long unitId, @RequestBody UnitRequest unitRequest) {
        return unitService.updateUnit(unitId, unitRequest);
    }

    @DeleteMapping("/{unitId}")
    public void deleteUnit(@PathVariable Long unitId) {
        unitService.deleteUnit(unitId);
    }
}
