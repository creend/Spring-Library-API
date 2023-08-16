package com.library.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter()
@Setter()
@Entity
@Table
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
    private String genre;
    private String publisher;
    private LocalDate releaseDate;

    private Integer availableCopies;
    private Double price;

    public BookEntity(Long id, String title, String author, String genre, String publisher, LocalDate releaseDate, Integer availableCopies, Double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.availableCopies = availableCopies;
        this.price = price;
    }

    public BookEntity(String title, String author,String genre, String publisher, LocalDate releaseDate, Integer availableCopies, Double price) {
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
