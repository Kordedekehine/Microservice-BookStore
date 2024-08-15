package com.bookstore.Book_Service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BooksResponseDto {

    private Long id;

    private String title;

    private String description;

    private String isbn;

    private String publisher;

    private List<GenreResponseDto> genreResponseDto;


}
