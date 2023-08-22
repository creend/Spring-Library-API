package com.library.api.service;

import com.library.api.dto.CreateBookDto;
import com.library.api.dto.UpdateBookDto;
import com.library.api.dto.mapper.CreateBookDtoMapper;
import com.library.api.dto.validation.DtoValidator;
import com.library.api.entity.BookEntity;
import com.library.api.entity.GenreEntity;
import com.library.api.exception.NotFoundException;
import com.library.api.model.BookFilter;
import com.library.api.repository.BookRepository;
import com.library.api.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final CreateBookDtoMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, CreateBookDtoMapper bookMapper, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.genreRepository = genreRepository;
    }
    public List<BookEntity> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public BookEntity getBookById(Long bookId) {
        return this.bookRepository.findById(bookId).orElseThrow(NotFoundException::new);
    }

    public void createBook(CreateBookDto bookDto) {
        DtoValidator.validate(bookDto);

        GenreEntity genre = genreRepository.findById(bookDto.getGenreId()).orElseThrow(()-> new NotFoundException(String.format("Genre with id %s doesnt exist",bookDto.getGenreId())));
        BookEntity bookEntity = bookMapper.fromDtoToEntity(bookDto);
        this.bookRepository.save(bookEntity);
    }

    @Transactional
    public void updateBook(UpdateBookDto bookDto, Long bookId) {
        DtoValidator.validate(bookDto);

        BookEntity bookEntity = this.bookRepository.findById(bookId).orElseThrow(NotFoundException::new);
        if(bookDto.getTitle() != null){
            bookEntity.setTitle(bookDto.getTitle());
        }
        if(bookDto.getAuthor() != null){
            bookEntity.setAuthor(bookDto.getAuthor());
        }
        if(bookDto.getGenreId() != null){
            GenreEntity genre = genreRepository.findById(bookDto.getGenreId()).orElseThrow(()-> new NotFoundException(String.format("Genre with id %s doesnt exist",bookDto.getGenreId())));
            bookEntity.setGenre(genre);
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
        if(bookFilter.getGenreId() != null){
            conditions = conditions.and(book -> book.getGenre().getId().equals(bookFilter.getGenreId()));
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
        List<BookEntity> books = this.bookRepository.findAll(Sort.by(bookFilter.getSortDirection(), bookFilter.getSortBy())).stream().filter(conditions).toList();
        if(books.isEmpty()){
            throw new NotFoundException("Not found Books with given criterias");
        }
        return books;
    }
}
