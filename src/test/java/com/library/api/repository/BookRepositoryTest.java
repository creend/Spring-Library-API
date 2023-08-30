package com.library.api.repository;

import com.library.api.entity.BookEntity;
import com.library.api.entity.GenreEntity;
import com.library.api.model.BookFields;
import com.library.api.model.BookFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void BookRepository_Save_ReturnSavedBook(){
        GenreEntity adventure = new GenreEntity("Adventure");
        BookEntity book = BookEntity.builder().title("W pustyni i w puszczy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();

        BookEntity savedBook = this.bookRepository.save(book);

        Assertions.assertNotEquals(null,savedBook);
        Assertions.assertTrue(savedBook.getId() > 0);
    }

    @Test
    public void BookRepository_GetAll_ReturnMoreThanOneBook(){
        GenreEntity adventure = new GenreEntity("Adventure");
        BookEntity book = BookEntity.builder().title("W pustyni i w puszczy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();
        BookEntity book2 = BookEntity.builder().title("Krzyzacy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();

        genreRepository.save(adventure);

        bookRepository.saveAll(List.of(book,book2));

        List<BookEntity> books = bookRepository.findAll();

        Assertions.assertNotNull(books);
        Assertions.assertEquals(2, books.size());
    }

    @Test
    public void BookRepository_FindById_ReturnOneBook(){
        GenreEntity adventure = new GenreEntity("Adventure");
        BookEntity book = BookEntity.builder().title("W pustyni i w puszczy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();
        genreRepository.save(adventure);
        bookRepository.save(book);

        Assertions.assertDoesNotThrow(() ->bookRepository.findById(book.getId()).orElseThrow());
    }

    @Test
    public void BookRepository_SearchBookEntitiesByGenreId_ReturnBooks(){
        GenreEntity adventure = new GenreEntity("Adventure");
        BookEntity book = BookEntity.builder().title("W pustyni i w puszczy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();
        BookEntity book2 = BookEntity.builder().title("Krzyzacy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();
        genreRepository.save(adventure);
        bookRepository.saveAll(List.of(book,book2));

        List<BookEntity> foundBooks = bookRepository.searchBookEntitiesByGenreId(adventure.getId());

        Assertions.assertNotNull(foundBooks);
        Assertions.assertEquals(2, foundBooks.size());

    }

    @Test
    public void BookRepository_SearchBookEntitiesByFilters_ByTitle_ReturnOneMatchingBook(){
        GenreEntity adventure = new GenreEntity("Adventure");
        BookEntity book = BookEntity.builder().title("W pustyni i w puszczy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();
        BookEntity book2 = BookEntity.builder().title("Krzyzacy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();
        genreRepository.save(adventure);
        bookRepository.saveAll(List.of(book,book2));

        BookFilter bookFilter = BookFilter.builder().title("Krzyz").sortBy(String.valueOf(BookFields.TITLE)).build();

        Pageable paging = PageRequest.of(0, 5);
        Page<BookEntity> foundBooks = bookRepository.searchBookEntitiesByFilters(bookFilter,paging);

        Assertions.assertNotNull(foundBooks);
        Assertions.assertEquals(1,foundBooks.getTotalElements());
        Assertions.assertEquals(1,foundBooks.getTotalPages());
        Assertions.assertEquals(book2.getId(),foundBooks.getContent().get(0).getId());
    }

    @Test
    public void BookRepository_UpdateBook_ReturnBookWithNewTitle(){
        GenreEntity adventure = new GenreEntity("Adventure");
        BookEntity book = BookEntity.builder().title("W pustyni i w puszczy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();

        genreRepository.save(adventure);
        bookRepository.save(book);

        BookEntity foundBook = bookRepository.findById(book.getId()).orElseThrow();
        foundBook.setTitle("Krzyzacy");

        BookEntity updatedBook = bookRepository.save(foundBook);

        Assertions.assertEquals("Krzyzacy",updatedBook.getTitle());
    }

    @Test
    public void BookRepository_DeleteBook_DeleteBookIsEmpty(){
        GenreEntity adventure = new GenreEntity("Adventure");
        BookEntity book = BookEntity.builder().title("W pustyni i w puszczy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();

        genreRepository.save(adventure);
        bookRepository.save(book);

        bookRepository.deleteById(book.getId());

        Optional<BookEntity> removedBook = bookRepository.findById(book.getId());

        Assertions.assertTrue(removedBook.isEmpty());
    }
}
