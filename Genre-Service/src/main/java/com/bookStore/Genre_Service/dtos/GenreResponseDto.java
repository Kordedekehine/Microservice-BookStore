package com.bookStore.Genre_Service.dtos;

import lombok.Data;

@Data
public class GenreResponseDto {

    private Long id;

    private String name;

    private String description;
}
