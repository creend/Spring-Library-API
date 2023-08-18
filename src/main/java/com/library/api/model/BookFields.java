package com.library.api.model;

import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.Locale;

@Getter
public enum BookFields {
    ID("id"),
    TITLE("title"),
    AUTHOR("author"),
    GENRE_ID("genreId"),
    PUBLISHER("publisher"),
    RELEASE_DATE("releaseDate"),
    AVAILABLE_COPIES("availableCopies"),
    PRICE("price");

    private String fieldName;
     BookFields(String fieldName){
        this.fieldName = fieldName;
    }

}
