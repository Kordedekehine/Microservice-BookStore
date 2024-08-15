package com.bookStore.Author_Service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.print.Book;
import java.util.List;


@Data
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String bio;

    private String bookTitle;

}