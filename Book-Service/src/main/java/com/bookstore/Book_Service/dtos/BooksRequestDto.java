package com.bookstore.Book_Service.dtos;

import lombok.Data;

@Data
public class BooksRequestDto {

    private String title;

    private String description;

    private String author;

    private String genre;
}
