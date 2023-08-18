package com.library.api.repository;

import com.library.api.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    @Query("SELECT g FROM GenreEntity g where g.name ILIKE %?1% ")
    List<GenreEntity> searchGenreEntities(String name);
}
