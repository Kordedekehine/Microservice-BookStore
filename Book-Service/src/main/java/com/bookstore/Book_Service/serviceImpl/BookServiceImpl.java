package com.bookstore.Book_Service.serviceImpl;


import com.bookstore.Book_Service.dtos.BooksRequestDto;
import com.bookstore.Book_Service.dtos.BooksResponseDto;
import com.bookstore.Book_Service.entity.Book;
import com.bookstore.Book_Service.exception.AlreadyExistsException;
import com.bookstore.Book_Service.exception.NotFoundException;
import com.bookstore.Book_Service.repository.BookRepository;
import com.bookstore.Book_Service.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public BooksResponseDto saveBook(BooksRequestDto bookDto) throws AlreadyExistsException, NotFoundException {

        Optional<Book> optionalBook = bookRepository.findBookByTitle(bookDto.getTitle());

        if (optionalBook.isPresent()){
            throw new AlreadyExistsException("Book already exists");
        }

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());

        bookRepository.save(book);

        return convertToBookResponseDto(book);
    }

    private BooksResponseDto convertToBookResponseDto(Book book) {
        return modelMapper.map(book, BooksResponseDto.class);
    }

    @Override
    public BooksResponseDto updateBook(Long id, BooksRequestDto bookDto) {

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()){
            throw new RuntimeException("Book not found");
        }

        Book book = optionalBook.get();
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());

        bookRepository.save(book);

        return convertToBookResponseDto(book);
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

        if (optionalBook.isEmpty()){
            throw new RuntimeException("Book not found");
        }

        Book book = optionalBook.get();

        bookRepository.delete(book);
    }
}
