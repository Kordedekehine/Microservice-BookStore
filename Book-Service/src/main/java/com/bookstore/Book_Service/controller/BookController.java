package com.bookstore.Book_Service.controller;


import com.bookstore.Book_Service.dtos.BooksRequestDto;
import com.bookstore.Book_Service.dtos.BooksResponseDto;
import com.bookstore.Book_Service.exception.AlreadyExistsException;
import com.bookstore.Book_Service.exception.NotFoundException;
import com.bookstore.Book_Service.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bookstore.Book_Service.util.Constants.*;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {


    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> registerBook(@RequestBody BooksRequestDto bookDto) throws AlreadyExistsException, NotFoundException, AlreadyExistsException, NotFoundException {

        BooksResponseDto saveBook = bookService.saveBook(bookDto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.CREATED.value());
        response.put("message", BOOK_CREATED_SUCCESS);
        response.put("data", saveBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


    @GetMapping("/{title}")
    ResponseEntity<Map<String, Object>> getBookByTitle(@PathVariable("title") String title)  throws NotFoundException {

        BooksResponseDto book = bookService.getBookByTitle(title);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", BOOK_RETRIEVED_SUCCESS);
        response.put("data", book);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBookDetails(@PathVariable("id") Long id, @RequestBody BooksRequestDto bookDto) {
        BooksResponseDto updateBook = bookService.updateBook(id, bookDto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", BOOK_UPDATED_SUCCESS);
        response.put("data", updateBook);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBooks(@PathVariable("id") Long id) {

        bookService.deleteBook(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.NO_CONTENT.value());
        response.put("message", BOOK_DELETED_SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }

    @GetMapping("/listBooks")
    public ResponseEntity<Map<String, Object>> getAllBooks() {
        List<BooksResponseDto> bookList = bookService.listAllBooks();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", BOOK_RETRIEVED_SUCCESS);
        response.put("data", bookList);
        return ResponseEntity.ok(response);
    }
}
