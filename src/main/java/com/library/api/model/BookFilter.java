package com.library.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BookFilter {
    private String title;
    private String author;
    private Long genreId;
    private String publisher;

    private Integer minAvailableCopies;
    private Double minPrice;
    private Double maxPrice;
    private Sort.Direction sortDirection;
    private String sortBy;

    public BookFilter(String title, String author, Long genreId, String publisher, Integer minAvailableCopies, Double minPrice, Double maxPrice, Sort.Direction sortDirection, String sortBy) {
        this.title = title;
        this.author = author;
        this.genreId = genreId;
        this.publisher = publisher;
        this.minAvailableCopies = minAvailableCopies;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sortDirection = sortDirection;
        try {
            this.sortBy = BookFields.valueOf(sortBy.toUpperCase()).getFieldName();
        }catch (IllegalArgumentException e){
            this.sortBy = BookFields.ID.getFieldName();
        }

    }

}
