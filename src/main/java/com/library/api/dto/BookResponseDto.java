package com.library.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BookResponseDto {
    long totalElements;
    int totalPages;
    int currentPage;
    List<BookDto> books;
}