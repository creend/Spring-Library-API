package com.library.api.dto.mapper;


import com.library.api.dto.CreateBookDto;
import com.library.api.entity.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookEntity fromDtoToEntity(CreateBookDto dto);

}
