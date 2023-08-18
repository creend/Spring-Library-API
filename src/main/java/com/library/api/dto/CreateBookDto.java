package com.library.api.dto;


import com.library.api.entity.GenreEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateBookDto {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private Long genreId;
    @NotNull
    private String publisher;
    @NotNull
    private LocalDate releaseDate;

    @PositiveOrZero
    private Integer availableCopies;
    @NotNull
    @Positive
    private Double price;
}
