package com.example.demo.controller;

import com.example.demo.dto.request.BookVoiceRequestDTO;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public Book createBook(@RequestBody BookVoiceRequestDTO requestDTO) {
        return bookService.createBookWithVoices(requestDTO);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody BookVoiceRequestDTO requestDTO) {
        return bookService.updateBook(id, requestDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
