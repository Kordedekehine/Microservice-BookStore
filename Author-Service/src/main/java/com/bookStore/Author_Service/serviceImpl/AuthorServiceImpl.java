package com.bookStore.Author_Service.serviceImpl;


import com.bookStore.Author_Service.client.BookClient;
import com.bookStore.Author_Service.dtos.AuthorDto;
import com.bookStore.Author_Service.dtos.AuthorResponseDto;
import com.bookStore.Author_Service.dtos.BooksResponseDto;
import com.bookStore.Author_Service.entity.Author;
import com.bookStore.Author_Service.exception.AlreadyExistsException;
import com.bookStore.Author_Service.exception.NotFoundException;
import com.bookStore.Author_Service.repository.AuthorRepository;
import com.bookStore.Author_Service.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookClient bookClient;

   @Autowired
    private ObjectMapper objectMapper;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public AuthorResponseDto saveAuthor(AuthorDto authorDto) throws AlreadyExistsException, NotFoundException {

        Optional<Author> optionalAuthor = authorRepository.findByName(authorDto.getName());

        if (optionalAuthor.isPresent()) {
            throw new AlreadyExistsException("AUTHOR ALREADY EXISTS");
        }

        ResponseEntity<Map<String, Object>> response = bookClient.getBookByTitle(authorDto.getBookTitle());

        if (response == null || response.getBody() == null) {
            throw new NotFoundException("BOOK DOES NOT EXIST! ADD THE BOOK FIRST");
        }

        Author author = new Author();
        author.setName(authorDto.getName());
        author.setBio(authorDto.getBio());
        author.setBookTitle(authorDto.getBookTitle());

        authorRepository.save(author);
        log.info("Author successfully saved to database");

        AuthorResponseDto authorResponseDto = modelMapper.map(author, AuthorResponseDto.class);

        BooksResponseDto bookDto = objectMapper.convertValue(response.getBody().get("data"), BooksResponseDto.class);

        authorResponseDto.setBooksResponseDto(Collections.singletonList(bookDto));

        return authorResponseDto;
    }

    @Override
    public AuthorResponseDto updateAuthor(Long id, AuthorDto authorDto) throws NotFoundException {

        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isEmpty()) {
            throw new RuntimeException("AUTHOR DOES NOT EXIST");
        }

        ResponseEntity<Map<String, Object>> response = bookClient.getBookByTitle(authorDto.getBookTitle());

        if (response == null || response.getBody() == null) {
            throw new NotFoundException("BOOK DOES NOT EXIST! ADD THE BOOK FIRST");
        }

        Author author = optionalAuthor.get();
        author.setName(authorDto.getName());
        author.setBio(authorDto.getBio());
        author.setBookTitle(author.getBookTitle());

        authorRepository.save(author);
        log.info("Author successfully updated");

        AuthorResponseDto authorResponseDto = modelMapper.map(author, AuthorResponseDto.class);

        BooksResponseDto bookDto = objectMapper.convertValue(response.getBody().get("data"), BooksResponseDto.class);

        authorResponseDto.setBooksResponseDto(Collections.singletonList(bookDto));

        return authorResponseDto;
    }

    public List<AuthorResponseDto> listAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(this::convertToAuthorResponseDto)
                .collect(Collectors.toList());
    }

    private AuthorResponseDto convertToAuthorResponseDto(Author author) {
        return modelMapper.map(author, AuthorResponseDto.class);
    }

    @Override
    public AuthorResponseDto getAuthorById(Long id) throws NotFoundException {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));


       // return convertToAuthorResponseDto(author);
        return  modelMapper.map(author,AuthorResponseDto.class);
    }



    @Override
    public void deleteAuthor(Long id) throws NotFoundException {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isEmpty()) {
            throw new NotFoundException("USER DOES NOT EXIST");
        }

        Author author = optionalAuthor.get();

        authorRepository.delete(author);
    }

    @Override
    public AuthorResponseDto getAuthorByBook(String bookTitle) throws NotFoundException {

        Optional<Author> author = authorRepository.findByBookTitle(bookTitle);

        if (author.isEmpty()) {
            throw new NotFoundException("AUTHOR NOT FOUND");
        }

        AuthorResponseDto authorResponseDto = modelMapper.map(author.get(), AuthorResponseDto.class);

        ResponseEntity<Map<String, Object>> response = bookClient.getBookByTitle(bookTitle);


        if (response == null || response.getBody() == null) {
            throw new NotFoundException("BOOK DOES NOT EXIST");
        }

        BooksResponseDto bookDto = objectMapper.convertValue(response.getBody().get("data"), BooksResponseDto.class);

        authorResponseDto.setBooksResponseDto(Collections.singletonList(bookDto));

        return authorResponseDto;
    }



}
