package com.bookstore.Book_Service.dtos;

import lombok.Data;

@Data
public class BooksResponseDto {

    private Long id;

    private String title;

    private String description;

    private String author;

    private String genre;
}
