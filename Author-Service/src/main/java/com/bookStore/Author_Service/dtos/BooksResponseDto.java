package com.bookStore.Author_Service.dtos;

import lombok.Data;

@Data
public class BooksResponseDto {

    private Long id;

    private String title;

    private String description;
    private String publisher;

    private String isbn;

}
