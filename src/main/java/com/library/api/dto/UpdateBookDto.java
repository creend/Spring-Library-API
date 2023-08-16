package com.library.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateBookDto {
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private LocalDate releaseDate;

    @PositiveOrZero
    private Integer availableCopies;
    @Positive
    private Double price;
}
