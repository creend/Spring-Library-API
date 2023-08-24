package com.library.api.service;

import com.library.api.dto.BookDto;
import com.library.api.dto.BookResponseDto;
import com.library.api.dto.CreateBookDto;
import com.library.api.dto.UpdateBookDto;
import com.library.api.dto.mapper.BookDtoMapper;
import com.library.api.dto.validation.DtoValidator;
import com.library.api.entity.BookEntity;
import com.library.api.entity.GenreEntity;
import com.library.api.exception.NotFoundException;
import com.library.api.model.BookFilter;
import com.library.api.repository.BookRepository;
import com.library.api.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final BookDtoMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookDtoMapper bookMapper, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.genreRepository = genreRepository;
    }
    public BookResponseDto getAllBooks(int page, int size) {
        List<BookEntity> books = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<BookEntity> pageBooks = this.bookRepository.findAll(paging);
        books = pageBooks.getContent();

        List<BookDto> mappedBooks = books.stream().map(bookMapper::fromEntityToDto).toList();

        return BookResponseDto.builder().totalPages(pageBooks.getTotalPages()).currentPage(page).books(mappedBooks).totalElements(pageBooks.getTotalElements()).build();
    }

    public BookDto getBookById(Long bookId) {
        return bookMapper.fromEntityToDto(this.bookRepository.findById(bookId).orElseThrow(NotFoundException::new));
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

    public BookResponseDto searchBooks(BookFilter bookFilter,int page,int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<BookEntity> pageBooks = this.bookRepository.searchBookEntitiesByFilters(bookFilter,paging);
        List<BookEntity> books = pageBooks.getContent();
        List<BookDto> mappedBooks = books.stream().map(bookMapper::fromEntityToDto).toList();

        return BookResponseDto.builder().totalPages(pageBooks.getTotalPages()).currentPage(page).books(mappedBooks).totalElements(pageBooks.getTotalElements()).build();
    }
}
