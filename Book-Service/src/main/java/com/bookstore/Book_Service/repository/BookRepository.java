package com.bookstore.Book_Service.repository;


import com.bookstore.Book_Service.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByTitle(String title);

    Optional<Book> findBookByGenreName(String genreName);
}
