package com.bookstore.Book_Service;

import com.bookstore.Book_Service.dtos.BooksRequestDto;
import com.bookstore.Book_Service.dtos.BooksResponseDto;
import com.bookstore.Book_Service.entity.Book;
import com.bookstore.Book_Service.exception.AlreadyExistsException;
import com.bookstore.Book_Service.exception.NotFoundException;
import com.bookstore.Book_Service.repository.BookRepository;
import com.bookstore.Book_Service.serviceImpl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceImplTest {


    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;


    @Test
    public void testCreateBook() throws AlreadyExistsException, NotFoundException {
        BooksRequestDto bookDto = new BooksRequestDto();
        Book book = new Book();
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BooksResponseDto result = bookService.saveBook(bookDto);
        assertNotNull(result);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = List.of(new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<BooksResponseDto> result = bookService.listAllBooks();
        assertEquals(1, result.size());
    }



    @Test
    public void testGetBooksByTitle() throws NotFoundException {
        Book books = new Book();
        when(bookRepository.findBookByTitle(anyString())).thenReturn(Optional.of(books));

        BooksResponseDto result = bookService.getBookByTitle("job");
        assertNotNull(result);
    }

    @Test
    public void testUpdateBooks()  {
        Book book = new Book();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BooksResponseDto result = bookService.updateBook(1L, new BooksRequestDto());
        assertNotNull(result);
    }

    @Test
    public void testDeleteProduct() {
        Book book = new Book();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(any(Book.class));

        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).delete(any(Book.class));
    }


}
