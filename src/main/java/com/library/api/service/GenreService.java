package com.library.api.service;

import com.library.api.dto.CreateGenreDto;
import com.library.api.dto.GenreDto;
import com.library.api.dto.UpdateGenreDto;
import com.library.api.dto.mapper.GenreDtoMapper;
import com.library.api.dto.validation.DtoValidator;
import com.library.api.entity.BookEntity;
import com.library.api.entity.GenreEntity;
import com.library.api.exception.BadRequestException;
import com.library.api.exception.NotFoundException;
import com.library.api.repository.BookRepository;
import com.library.api.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final GenreDtoMapper genreMapper;

    @Autowired
    public GenreService(GenreRepository genreRepository, BookRepository bookRepository, GenreDtoMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.genreMapper = genreMapper;
    }

    public List<GenreEntity> getAllGenres(){
        return this.genreRepository.findAll();
    }

    public GenreDto getGenreById(Long genreId) {
        if(!this.genreRepository.existsById(genreId)){
            throw new NotFoundException(String.format("Genre with id %d not found",genreId));
        }
        return genreMapper.fromEntityToDto(this.genreRepository.findById(genreId).orElseThrow());
    }

    public List<GenreEntity> searchGenres(String name) {
        return this.genreRepository.searchGenreEntities(name);
    }

    public void createGenre(CreateGenreDto genreDto) {
        DtoValidator.validate(genreDto);
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

    @Transactional
    public void deleteGenreAndBreak(Long genreId) {
        GenreEntity genre = genreRepository.findById(genreId).orElseThrow(()-> new NotFoundException(String.format("Genre with id %d not found",genreId)));
        List<BookEntity> booksWithGivenGenre = genre.getBooks();
        for(BookEntity book:booksWithGivenGenre){
            book.setGenre(null);
        }
        genreRepository.deleteById(genreId);
    }

    @Transactional()
    public void updateGenre(Long genreId, UpdateGenreDto updateGenreDto) {
//        DtoValidator.validate(updateGenreDto);

        GenreEntity genre = genreRepository.findById(genreId).orElseThrow(()-> new NotFoundException(String.format("Genre with id %d not found",genreId)));
        if (updateGenreDto.getName()!=null){
            genre.setName(updateGenreDto.getName());
        }
    }


}
