package com.example.demo.service;

import com.example.demo.dto.request.TestPartRequest;
import com.example.demo.dto.response.TestPartResponse;
import com.example.demo.entity.Test;
import com.example.demo.entity.TestPart;
import com.example.demo.repository.TestPartRepository;
import com.example.demo.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestPartService {

    private final TestRepository testRepository;
    private final TestPartRepository testPartRepository;

    public TestPartResponse createPart(Long testId, TestPartRequest request) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new RuntimeException("Test not found"));
        TestPart part = TestPart.builder()
                .type(request.getType())
                .test(test)
                .build();
        return mapToDto(testPartRepository.save(part));
    }

    public TestPartResponse updatePart(Long partId, TestPartRequest request) {
        TestPart part = testPartRepository.findById(partId).orElseThrow(() -> new RuntimeException("Part not found"));
        part.setType(request.getType());
        return mapToDto(testPartRepository.save(part));
    }

    public void deletePart(Long partId) {
        testPartRepository.deleteById(partId);
    }

    private TestPartResponse mapToDto(TestPart part) {
        return TestPartResponse.builder()
                .id(part.getId())
                .type(part.getType())
                .build();
    }
}
