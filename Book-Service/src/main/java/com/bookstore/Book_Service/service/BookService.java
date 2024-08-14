package com.bookstore.Book_Service.service;




import com.bookstore.Book_Service.dtos.BooksRequestDto;
import com.bookstore.Book_Service.dtos.BooksResponseDto;
import com.bookstore.Book_Service.exception.AlreadyExistsException;
import com.bookstore.Book_Service.exception.NotFoundException;

import java.util.List;

public interface BookService {


    BooksResponseDto saveBook(BooksRequestDto bookDto) throws AlreadyExistsException, NotFoundException;

    BooksResponseDto updateBook(Long id, BooksRequestDto bookDto);

    List<BooksResponseDto> listAllBooks();

    BooksResponseDto getBookByTitle(String title) throws NotFoundException;

    void deleteBook(Long id);
}
