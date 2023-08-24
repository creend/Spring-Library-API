package com.library.api.repository;

import com.library.api.entity.BookEntity;
import com.library.api.model.BookFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> searchBookEntitiesByGenreId(Long genreId);

    @Query("SELECT b FROM BookEntity b WHERE (:#{#bookFilter.title} is null OR b.title ilike %:#{#bookFilter.title}%) " +
            "AND (:#{#bookFilter.author} is null OR b.author ilike %:#{#bookFilter.author}%) " +
            "AND (:#{#bookFilter.genreId} is null OR b.genre.id = :#{#bookFilter.genreId}) " +
            "AND (:#{#bookFilter.publisher} is null OR b.publisher ilike %:#{#bookFilter.publisher}%) " +
            "AND (:#{#bookFilter.minAvailableCopies} is null OR b.availableCopies >= :#{#bookFilter.minAvailableCopies}) " +
            "AND (:#{#bookFilter.minPrice} is null OR b.price > :#{#bookFilter.minPrice}) " +
            "AND (:#{#bookFilter.maxPrice} is null OR b.price < :#{#bookFilter.maxPrice})")
    Page<BookEntity> searchBookEntitiesByFilters(@Param("bookFilter") BookFilter bookFilter, Pageable paging);
}
