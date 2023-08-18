package com.library.api.controller;

import com.library.api.entity.GenreEntity;
import com.library.api.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/genre")
@Validated
public class GenreController {
    private final GenreService genreService;
    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }
    @GetMapping
    public ResponseEntity<List<GenreEntity>> getAllGenres(){
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/search")
    public ResponseEntity<List<GenreEntity>> searchGenres(
            @RequestParam(required = false) String name
    ){
        return ResponseEntity.ok(genreService.searchGenres(name));
    }

    @GetMapping(path = "{genreId}")
    public ResponseEntity<GenreEntity> getGenreById(
            @PathVariable("genreId") Long genreId
    ){
        return ResponseEntity.ok(genreService.getGenreById(genreId));
    }

}
