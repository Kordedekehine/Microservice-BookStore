package com.bookStore.Author_Service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AuthorResponseDto {

    private Long id;

    private String name;

    private String Bio;

    private String bookTitle;

    private List<BooksResponseDto> booksResponseDto;

}
