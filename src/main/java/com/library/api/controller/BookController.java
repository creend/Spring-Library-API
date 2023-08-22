package com.library.api.controller;

import com.library.api.dto.CreateBookDto;
import com.library.api.dto.UpdateBookDto;
import com.library.api.entity.BookEntity;
import com.library.api.exception.BadRequestException;
import com.library.api.model.BookFields;
import com.library.api.model.BookFilter;
import com.library.api.service.BookService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/book")
@Validated
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

    @GetMapping("/search")
    public ResponseEntity<List<BookEntity>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Integer minAvailableCopies,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(defaultValue = "id") String sortBy

    ){
        BookFilter bookFilter = new BookFilter(title,author,genreId,publisher,minAvailableCopies,minPrice,maxPrice, sortDirection,sortBy);
        return ResponseEntity.ok(bookService.searchBooks(bookFilter));
    }

    @GetMapping(path = "{bookId}")
    public ResponseEntity<BookEntity> getBookById(
            @Min(1) @PathVariable("bookId") Long bookId
    ){
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @PostMapping
    public ResponseEntity<Void> createBook(
            @Valid @RequestBody() CreateBookDto bookDto
    ){
        bookService.createBook(bookDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "{bookId}")
    public ResponseEntity<Void> updateBook(
            @Valid @RequestBody() UpdateBookDto bookDto,
            @Min(1) @PathVariable("bookId") Long bookId
    ){
        bookService.updateBook(bookDto,bookId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{bookId}")
    public ResponseEntity<Void> deleteBook(
            @Min(1) @PathVariable("bookId") Long bookId
    ){
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

}
