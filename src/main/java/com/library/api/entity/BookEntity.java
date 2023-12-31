package com.library.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter()
@Setter()
@Entity
@Table
@Builder
public class BookEntity {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    private Long id;

    private String title;
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="genreId",referencedColumnName = "id")
    private GenreEntity genre;

    private String publisher;
    private LocalDate releaseDate;

    private Integer availableCopies;
    private Double price;

    public BookEntity(Long id, String title, String author, GenreEntity genre, String publisher, LocalDate releaseDate, Integer availableCopies, Double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.availableCopies = availableCopies;
        this.price = price;
    }

    public BookEntity(String title, String author,GenreEntity genre, String publisher, LocalDate releaseDate, Integer availableCopies, Double price) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.availableCopies = availableCopies;
        this.price = price;
    }

    public BookEntity() {

    }
}
