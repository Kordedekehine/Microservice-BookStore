package com.bookStore.Author_Service.controller;


import com.bookStore.Author_Service.dtos.AuthorDto;
import com.bookStore.Author_Service.dtos.AuthorResponseDto;
import com.bookStore.Author_Service.exception.AlreadyExistsException;
import com.bookStore.Author_Service.exception.NotFoundException;
import com.bookStore.Author_Service.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bookStore.Author_Service.util.Constants.*;


@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;



    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> registerAuthor(@RequestBody AuthorDto authorDto) throws AlreadyExistsException, NotFoundException {

        AuthorResponseDto saveAuthor = authorService.saveAuthor(authorDto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.CREATED.value());
        response.put("message", AUTHOR_CREATED_SUCCESS);
        response.put("data", saveAuthor);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


    @GetMapping("getAuthorById/{id}")
    ResponseEntity<Map<String, Object>> getAuthorById(@PathVariable("id") Long id)  throws NotFoundException {

        AuthorResponseDto author = authorService.getAuthorById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", AUTHOR_RETRIEVED_SUCCESS);
        response.put("data", author);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAuthorByBook/{title}")
    ResponseEntity<Map<String, Object>> getAuthorByBook(@PathVariable("title") String bookTitle)  throws NotFoundException {

        AuthorResponseDto author = authorService.getAuthorByBook(bookTitle);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", AUTHOR_RETRIEVED_SUCCESS);
        response.put("data", author);
        return ResponseEntity.ok(response);

        }


    @PutMapping("updateAuthor/{id}")
    public ResponseEntity<Map<String, Object>> updateAuthorDetails(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) throws NotFoundException {
        AuthorResponseDto updateAuthor = authorService.updateAuthor(id, authorDto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", AUTHORS_UPDATED_SUCCESS);
        response.put("data", updateAuthor);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("deleteAuthor/{id}")
    public ResponseEntity<Map<String, Object>> deleteAuthor(@PathVariable("id") Long id) throws NotFoundException {

        authorService.deleteAuthor(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.NO_CONTENT.value());
        response.put("message", AUTHORS_DELETED_SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }


    @GetMapping("/listAuthors")
    public ResponseEntity<Map<String, Object>> getAllAuthors() {
        List<AuthorResponseDto> author = authorService.listAllAuthors();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", AUTHOR_RETRIEVED_SUCCESS);
        response.put("data", author);
        return ResponseEntity.ok(response);
    }

}
