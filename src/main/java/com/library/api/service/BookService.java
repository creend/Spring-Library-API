package com.library.api.service;

import com.library.api.dto.CreateBookDto;
import com.library.api.dto.UpdateBookDto;
import com.library.api.dto.mapper.BookMapper;
import com.library.api.entity.BookEntity;
import com.library.api.exception.NotFoundException;
import com.library.api.model.BookFilter;
import com.library.api.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.function.Predicate;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }
    public List<BookEntity> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public BookEntity getBookById(Long bookId) {
        return this.bookRepository.findById(bookId).orElseThrow(NotFoundException::new);
    }

    public void createBook(CreateBookDto bookDto) {
        BookEntity bookEntity = bookMapper.fromDtoToEntity(bookDto);
        this.bookRepository.save(bookEntity);
    }

    @Transactional
    public void updateBook(UpdateBookDto bookDto, Long bookId) {
        BookEntity bookEntity = this.bookRepository.findById(bookId).orElseThrow(NotFoundException::new);
        if(bookDto.getTitle() != null){
            bookEntity.setTitle(bookDto.getTitle());
        }
        if(bookDto.getAuthor() != null){
            bookEntity.setAuthor(bookDto.getAuthor());
        }
        if(bookDto.getGenre() != null){
            bookEntity.setGenre(bookDto.getGenre());
        }
        if(bookDto.getPublisher() != null){
            bookEntity.setPublisher(bookDto.getPublisher());
        }
        if(bookDto.getReleaseDate() != null){
            bookEntity.setReleaseDate(bookDto.getReleaseDate());
        }
        if(bookDto.getAvailableCopies() != null){
            bookEntity.setAvailableCopies(bookDto.getAvailableCopies());
        }
        if(bookDto.getPrice() != null){
            bookEntity.setPrice(bookDto.getPrice());
        }
    }

    public void deleteBook(Long bookId) {
        if(!this.bookRepository.existsById(bookId)){
            throw  new NotFoundException(String.format("Book with id %d doesnt exist", bookId));
        }
        this.bookRepository.deleteById(bookId);
    }

    public List<BookEntity> searchBooks(BookFilter bookFilter) {
        Predicate<BookEntity> conditions = e -> true;
        if(bookFilter.getTitle() != null){
            conditions = conditions.and(book -> book.getTitle().toLowerCase().contains(bookFilter.getTitle().toLowerCase()));
        }
        if(bookFilter.getAuthor() != null){
            conditions = conditions.and(book -> book.getAuthor().toLowerCase().contains(bookFilter.getAuthor().toLowerCase()));
        }
        if(bookFilter.getGenre() != null){
            conditions = conditions.and(book -> book.getGenre().equalsIgnoreCase(bookFilter.getGenre()));
        }
        if(bookFilter.getPublisher() != null){
            conditions = conditions.and(book -> book.getPublisher().equalsIgnoreCase(bookFilter.getPublisher()));
        }
        if(bookFilter.getMinAvailableCopies() != null){
            conditions = conditions.and(book -> book.getAvailableCopies() >= bookFilter.getMinAvailableCopies());
        }
        if(bookFilter.getMinPrice() != null){
            conditions = conditions.and(book -> book.getPrice() >= bookFilter.getMinPrice());
        }
        if(bookFilter.getMaxPrice() != null){
            conditions = conditions.and(book -> book.getPrice() <= bookFilter.getMaxPrice());
        }
        List<BookEntity> books = this.bookRepository.findAll().stream().filter(conditions).toList();
        if(books.isEmpty()){
            throw new NotFoundException("Not found Books with given criterias");
        }
        return books;
    }
}
