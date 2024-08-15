package com.bookstore.Book_Service.serviceImpl;


import com.bookstore.Book_Service.client.GenreClient;
import com.bookstore.Book_Service.dtos.BooksRequestDto;
import com.bookstore.Book_Service.dtos.BooksResponseDto;
import com.bookstore.Book_Service.dtos.GenreResponseDto;
import com.bookstore.Book_Service.entity.Book;
import com.bookstore.Book_Service.exception.AlreadyExistsException;
import com.bookstore.Book_Service.exception.NotFoundException;
import com.bookstore.Book_Service.repository.BookRepository;
import com.bookstore.Book_Service.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreClient genreClient;

    ObjectMapper objectMapper = new ObjectMapper();

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public BooksResponseDto saveBook(BooksRequestDto bookDto) throws AlreadyExistsException, NotFoundException {

        Optional<Book> optionalBook = bookRepository.findBookByTitle(bookDto.getTitle());

        if (optionalBook.isPresent()) {
            throw new AlreadyExistsException("Book already exists");
        }


        //check if genre exist first, by checking the genre service
        ResponseEntity<Map<String, Object>> response = genreClient.getGenreByName(bookDto.getGenreName());

        if (response == null || response.getBody() == null) {
            throw new NotFoundException("GENRE DOES NOT EXIST! CREATE THE GENRE FIRST");
        }

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setIsbn(bookDto.getIsbn());
        book.setPublisher(bookDto.getPublisher());
        book.setGenreName(bookDto.getGenreName());
        bookRepository.save(book);

        BooksResponseDto bookResponseDto = new ModelMapper().map(book, BooksResponseDto.class);

        GenreResponseDto genreDto = objectMapper.convertValue(response.getBody().get("data"), GenreResponseDto.class);

        bookResponseDto.setGenreResponseDto(Collections.singletonList(genreDto));

        return bookResponseDto;
    }

    private BooksResponseDto convertToBookResponseDto(Book book) {
        return modelMapper.map(book, BooksResponseDto.class);
    }

    @Override
    public BooksResponseDto updateBook(Long id, BooksRequestDto bookDto) throws NotFoundException {

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()) {
            throw new RuntimeException("Book not found");
        }

        ResponseEntity<Map<String, Object>> response = genreClient.getGenreByName(bookDto.getGenreName());

        if (response == null || response.getBody() == null) {
            throw new NotFoundException("THE UPDATED GENRE DOES NOT EXIST! CREATE THE GENRE FIRST");
        }

        Book book = optionalBook.get();
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setPublisher(bookDto.getPublisher());
        book.setIsbn(bookDto.getIsbn());

        bookRepository.save(book);

        BooksResponseDto bookResponseDto = new ModelMapper().map(book, BooksResponseDto.class);

        GenreResponseDto genreDto = objectMapper.convertValue(response.getBody().get("data"), GenreResponseDto.class);

        bookResponseDto.setGenreResponseDto(Collections.singletonList(genreDto));

        return bookResponseDto;
    }

    @Override
    public List<BooksResponseDto> listAllBooks() {

        List<Book> books = (List<Book>) bookRepository.findAll();

        return books.stream()
                .map(this::convertToBookResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BooksResponseDto getBookByTitle(String title) throws NotFoundException {

        Book book = bookRepository.findBookByTitle(title)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        return convertToBookResponseDto(book);
    }

    @Override
    public void deleteBook(Long id) {

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()) {
            throw new RuntimeException("Book not found");
        }

        Book book = optionalBook.get();

        bookRepository.delete(book);
    }

    @Override
    public BooksResponseDto getBookByGenre(String name) throws NotFoundException {

        Optional<Book> book = bookRepository.findBookByGenreName(name);

        if (book.isEmpty()) {
            throw new NotFoundException("BOOK NOT FOUND");
        }

        BooksResponseDto bookResponseDto = modelMapper.map(book.get(), BooksResponseDto.class);

        ResponseEntity<Map<String, Object>> response = genreClient.getGenreByName(name);

        if (response == null || response.getBody() == null) {
            throw new NotFoundException("GENRE DOES NOT EXIST! CREATE THE GENRE FIRST");
        }


        GenreResponseDto genreDto = objectMapper.convertValue(response.getBody().get("data"), GenreResponseDto.class);

        bookResponseDto.setGenreResponseDto((Collections.singletonList(genreDto)));

        return bookResponseDto;
    }

}