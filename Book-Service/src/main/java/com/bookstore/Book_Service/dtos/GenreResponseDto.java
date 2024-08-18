package com.bookstore.Book_Service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GenreResponseDto {

    private Long id;


    private String name;

    private String description;

}
