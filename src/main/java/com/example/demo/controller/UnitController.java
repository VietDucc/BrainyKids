package com.example.demo.controller;

import com.example.demo.dto.request.UnitRequest;
import com.example.demo.dto.response.UnitResponse;
import com.example.demo.entity.Unit;
import com.example.demo.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController {
    @Autowired
    private UnitService unitService;

    @GetMapping("/{courseId}")
    public List<UnitResponse.UnitDTO> getUnitsByCourseId(@PathVariable Long courseId) {
        return unitService.getUnitsByCourseId(courseId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{courseId}")
    public Unit createUnit(@PathVariable Long courseId, @RequestBody UnitRequest unitRequest) {
        return unitService.createUnit(courseId, unitRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{unitId}")
    public Unit updateUnit(@PathVariable Long unitId, @RequestBody UnitRequest unitRequest) {
        return unitService.updateUnit(unitId, unitRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{unitId}")
    public void deleteUnit(@PathVariable Long unitId) {
        unitService.deleteUnit(unitId);
    }
}
