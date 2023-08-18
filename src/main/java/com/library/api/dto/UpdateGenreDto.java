package com.library.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGenreDto extends CreateGenreDto{
    @NotNull
    private String name;
}
