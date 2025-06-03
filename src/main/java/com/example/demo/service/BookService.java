package com.example.demo.service;

import com.example.demo.dto.request.BookVoiceRequestDTO;
import com.example.demo.dto.request.AudioVoiceDTO;
import com.example.demo.entity.Book;
import com.example.demo.entity.Voice;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.VoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private VoiceRepository voiceRepository;

    public Book createBookWithVoices(BookVoiceRequestDTO dto) {
        Book book = new Book();
        book.setPdfUrl(dto.getPdfUrl());
        book.setTitle(dto.getTitle());

        List<Voice> voiceList = new ArrayList<>();
        for (AudioVoiceDTO voiceDTO : dto.getVoices()) {
            Voice voice = new Voice();
            voice.setVoiceUrl(voiceDTO.getVoiceUrl());
            voice.setPageIndex(voiceDTO.getPageIndex());
            voice.setBook(book);
            voiceList.add(voice);
        }

        book.setVoices(voiceList);

        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book updateBook(Long id, BookVoiceRequestDTO requestDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(requestDTO.getTitle());
        book.setPdfUrl(requestDTO.getPdfUrl());

        // Nếu muốn cập nhật lại danh sách voice
        book.getVoices().clear();
        requestDTO.getVoices().forEach(voiceDTO -> {
            Voice voice = new Voice();
            voice.setVoiceUrl(voiceDTO.getVoiceUrl());
            voice.setPageIndex(voiceDTO.getPageIndex());
            voice.setBook(book); // set quan hệ ngược lại
            book.getVoices().add(voice);
        });

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

}
