package com.bookstore.Book_Service.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false,unique = true)
  private String title;


  private String description;


  private String author;


  private String genre;

}
