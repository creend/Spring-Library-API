package com.library.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookFilter {
    private String title;
    private String author;
    private Long genreId;
    private String publisher;

    private Integer minAvailableCopies;
    private Double minPrice;
    private Double maxPrice;

    public BookFilter(String title, String author, Long genreId, String publisher, Integer minAvailableCopies, Double minPrice, Double maxPrice) {
        this.title = title;
        this.author = author;
        this.genreId = genreId;
        this.publisher = publisher;
        this.minAvailableCopies = minAvailableCopies;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

}
