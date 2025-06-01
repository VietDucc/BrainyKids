package com.example.demo.dto.response;

import com.example.demo.dto.request.TestPartRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResponse {
    private Long id;
    private String name;
    private String description;
    private List<TestPartResponse> parts;
}
