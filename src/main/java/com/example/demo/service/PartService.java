package com.example.demo.service;

import com.example.demo.dto.request.PartRequest;
import com.example.demo.entity.Exam;
import com.example.demo.entity.Part;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartService {
    @Autowired
    private PartRepository partRepository;

    @Autowired
    private ExamRepository examRepository;

    public List<Part> getPartsByExamId(Long examId) {
        return partRepository.findByExam_Id(examId);
    }

    public Part createPart(PartRequest partRequest) {
        Exam exam = examRepository.findById(partRequest.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Part part = Part.builder()
                .description(partRequest.getDescription())
                .partOrder(partRequest.getPartOrder())
                .partNumber(partRequest.getPartNumber())
                .type(partRequest.getType())
                .exam(exam)
                .build();

        return partRepository.save(part);
    }

    public Part updatePart(Long partId, PartRequest partRequest) {
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new RuntimeException("Part not found"));

        part.setDescription(partRequest.getDescription());
        part.setPartOrder(partRequest.getPartOrder());
        part.setPartNumber(partRequest.getPartNumber());
        part.setType(partRequest.getType());

        return partRepository.save(part);
    }

    public void deletePart(Long partId) {
        if (!partRepository.existsById(partId)) {
            throw new RuntimeException("Part not found");
        }
        partRepository.deleteById(partId);
    }
}