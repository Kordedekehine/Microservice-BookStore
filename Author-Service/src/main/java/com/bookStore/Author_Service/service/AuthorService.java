package com.bookStore.Author_Service.service;


import com.bookStore.Author_Service.dtos.AuthorDto;
import com.bookStore.Author_Service.dtos.AuthorResponseDto;
import com.bookStore.Author_Service.exception.AlreadyExistsException;
import com.bookStore.Author_Service.exception.NotFoundException;

import java.util.List;

public interface AuthorService {

    AuthorResponseDto saveAuthor(AuthorDto authorDto) throws AlreadyExistsException, NotFoundException;

    AuthorResponseDto updateAuthor(Long id, AuthorDto authorDto) throws NotFoundException;

    List<AuthorResponseDto> listAllAuthors();

    AuthorResponseDto getAuthorById(Long id) throws NotFoundException;

    void deleteAuthor(Long id) throws NotFoundException;

    //using feign to communicate to our book service
    AuthorResponseDto getAuthorByBook(String bookTitle) throws NotFoundException;


}
