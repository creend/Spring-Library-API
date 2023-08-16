package com.library.api.controller;

import com.library.api.dto.CreateBookDto;
import com.library.api.dto.UpdateBookDto;
import com.library.api.entity.BookEntity;
import com.library.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    public ResponseEntity<List<BookEntity>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping(path = "{bookId}")
    public ResponseEntity<BookEntity> getBookById(
            @PathVariable("bookId") Long bookId
    ){
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @PostMapping
    public ResponseEntity<Void> createBook(
            @RequestBody() CreateBookDto bookDto
    ){
        bookService.createBook(bookDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "{bookId}")
    public ResponseEntity<Void> updateBook(
            @RequestBody() UpdateBookDto bookDto,
            @PathVariable("bookId") Long bookId
    ){
        bookService.updateBook(bookDto,bookId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{bookId}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable("bookId") Long bookId
    ){
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }
}
