package com.library.api.dto.mapper;


import com.library.api.dto.CreateBookDto;
import com.library.api.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateBookDtoMapper {

    @Mapping(target = "genre.id", source = "genreId")
    BookEntity fromDtoToEntity(CreateBookDto dto);

}
