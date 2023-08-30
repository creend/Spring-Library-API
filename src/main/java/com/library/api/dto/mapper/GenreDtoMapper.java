package com.library.api.dto.mapper;

import com.library.api.dto.BookDto;
import com.library.api.dto.CreateBookDto;
import com.library.api.dto.GenreDto;
import com.library.api.entity.BookEntity;
import com.library.api.entity.GenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreDtoMapper {
//    @Mapping(source = "genre.id", target = "id")
//    @Mapping(source = "name", target = "name")
    GenreDto fromEntityToDto(GenreEntity entity);
}
