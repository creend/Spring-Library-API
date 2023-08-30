package com.library.api.repository;

import com.library.api.entity.BookEntity;
import com.library.api.entity.GenreEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GenreRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void GenreRepository_Save_ReturnSavedGenre(){
        GenreEntity adventure = new GenreEntity("Adventure");
        GenreEntity savedGenre = genreRepository.save(adventure);

        Assertions.assertThat(savedGenre).isNotNull();
        Assertions.assertThat(savedGenre.getId()).isGreaterThan(0);
    }

    @Test
    public void GenreRepository_FindAll_ReturnMoreThanOneGenre(){
        GenreEntity adventure = new GenreEntity("Adventure");
        GenreEntity huj = new GenreEntity("huj");
        genreRepository.saveAll(List.of(adventure,huj));

        List<GenreEntity> savedGenres = genreRepository.findAll();

        Assertions.assertThat(savedGenres).isNotNull();
        Assertions.assertThat(savedGenres.size()).isGreaterThan(1);
    }
    @Test
    public void GenreRepository_searchGenreEntities_ReturnFittingGenre(){
        GenreEntity adventure = new GenreEntity("Adventure");
        GenreEntity huj = new GenreEntity("huj");
        genreRepository.saveAll(List.of(adventure,huj));

        List<GenreEntity> foundGenres = genreRepository.searchGenreEntities("ventuR");

        Assertions.assertThat(foundGenres).isNotNull();
        Assertions.assertThat(foundGenres.size()).isGreaterThan(0);
        Assertions.assertThat(foundGenres.get(0).getName()).isEqualTo("Adventure");
    }

    @Test
    public void GenreRepository_removeGenre(){
        GenreEntity adventure = new GenreEntity("Adventure");
        BookEntity book = BookEntity.builder().title("W pustyni i w puszczy").price(50.0).availableCopies(100).genre(adventure).publisher("GREG").releaseDate(
                LocalDate.of(2015, Month.AUGUST,31)
        ).author("Henryk Sienkiewicz").build();

        genreRepository.save(adventure);
        bookRepository.save(book);

        genreRepository.deleteById(adventure.getId());

        Optional<GenreEntity> removedGenre = genreRepository.findById(adventure.getId());

        Assertions.assertThat(removedGenre).isEmpty();

    }
}
