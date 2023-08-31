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
import com.library.api.repository.BookRepository;
import com.library.api.repository.GenreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookDtoMapper bookMapper;


    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void BookService_FindAllBooks_ReturnsResponseDto(){
        Page<BookEntity> books = Mockito.mock(Page.class);
        List<BookEntity> booksList = Mockito.mock(List.class);

        Mockito.when(bookRepository.findAll(Mockito.any(Pageable.class))).thenReturn(books);

        BookResponseDto bookResponse = bookService.getAllBooks(0,5);

        Assertions.assertThat(bookResponse).isNotNull();
    }

    @Test
    public void BookService_CreateBook_NotThrowsError(){
        GenreEntity adventure = new GenreEntity("Adventure");

        CreateBookDto bookDto = Mockito.mock(CreateBookDto.class);

        try (MockedStatic<DtoValidator> mocked = Mockito.mockStatic(DtoValidator.class)) {
            mocked.when(()->DtoValidator.validate(bookDto)).thenAnswer(invocationOnMock -> {
                return null;
            });
            Mockito.when(genreRepository.findById(Mockito.any(long.class))).thenReturn(Optional.of(adventure));
            assertAll(()->bookService.createBook(bookDto));
        }

    }

    @Test
    public void BookService_FindBookById_ReturnsBookDto(){
        GenreEntity adventure = new GenreEntity("Adventure");
        BookEntity book = BookEntity.builder().title("W pustyni i w puszczy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();


        Mockito.when(bookRepository.findById(Mockito.any(long.class))).thenReturn(Optional.ofNullable(book));
        Mockito.when(bookMapper.fromEntityToDto(Mockito.any(BookEntity.class))).thenReturn(Mockito.mock(BookDto.class));

        BookDto bookDto = bookService.getBookById(1L);

        Assertions.assertThat(bookDto).isNotNull();
    }

    @Test
    public void BookService_UpdateBookById_NotThrowsErrorWhenFoundAndThrowsIfNot(){
        long bookId = 1L;
        GenreEntity adventure = new GenreEntity("Adventure");
        BookEntity book = BookEntity.builder().title("W pustyni i w puszczy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").id(bookId).build();

        UpdateBookDto updateBookDto = UpdateBookDto.builder().title("Krzyzacy").build();
        Mockito.when(bookRepository.findById(2L)).thenThrow(NotFoundException.class);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));

       assertAll(()->bookService.updateBook(updateBookDto,bookId));
       Assertions.assertThatThrownBy(()->bookService.updateBook(updateBookDto,2L));
    }

    @Test
    public void BookService_DeleteBookById_NotThrowsErrorWhenFoundAndThrowsIfNot(){
        long bookId = 1L;
        GenreEntity adventure = new GenreEntity("Adventure");
        BookEntity book = BookEntity.builder().title("W pustyni i w puszczy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").id(bookId).build();

        Mockito.when(bookRepository.existsById(2L)).thenReturn(false);
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);
        assertAll(()->bookService.deleteBook(bookId));
        Assertions.assertThatThrownBy(()->bookService.deleteBook(2L));
    }

}
