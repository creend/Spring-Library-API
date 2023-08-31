package com.library.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BookDto {
    private Long id;

    private String title;
    private String author;
    private String genre;
    private String publisher;
    private LocalDate releaseDate;

    private Integer availableCopies;
    private Double price;
}
