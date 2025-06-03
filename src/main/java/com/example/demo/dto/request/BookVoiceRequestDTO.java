package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookVoiceRequestDTO {
    private String pdfUrl;
    private String title;
    private List<AudioVoiceDTO> voices;
}
