package com.library.api.service;

import com.library.api.dto.CreateGenreDto;
import com.library.api.entity.GenreEntity;
import com.library.api.exception.BadRequestException;
import com.library.api.exception.NotFoundException;
import com.library.api.repository.BookRepository;
import com.library.api.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
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

    public void createGenre(CreateGenreDto genreDto) {
        GenreEntity genre = new GenreEntity(genreDto.getName());
        genreRepository.save(genre);
    }

    public void deleteGenre(Long genreId) {
        GenreEntity genre = genreRepository.findById(genreId).orElseThrow(()-> new NotFoundException(String.format("Genre with id %d not found",genreId)));
        int booksWithGivenGenre = this.bookRepository.searchBookEntitiesByGenreId(genreId).size();
        if(booksWithGivenGenre > 0){
            throw new BadRequestException(String.format("Genre with id %d is already used by %d books",genreId,booksWithGivenGenre));
        }
        genreRepository.deleteById(genreId);
    }
}
