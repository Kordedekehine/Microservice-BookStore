package com.bookStore.Author_Service.repository;


import com.bookStore.Author_Service.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

  Optional<Author> findByName(String name);

  Optional<Author> findByBookTitle(String bookTitle);


}
