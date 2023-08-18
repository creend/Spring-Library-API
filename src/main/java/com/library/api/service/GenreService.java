package com.library.api.service;

import com.library.api.entity.GenreEntity;
import com.library.api.exception.NotFoundException;
import com.library.api.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreEntity> getAllGenres(){
        return this.genreRepository.findAll();
    }

    public GenreEntity getGenreById(Long genreId) {
        return this.genreRepository.findById(genreId).orElseThrow(()-> new NotFoundException(String.format("Genre with id %d not found",genreId)));
    }

    public List<GenreEntity> searchGenres(String name) {
        return this.genreRepository.searchGenreEntities(name);
    }
}
