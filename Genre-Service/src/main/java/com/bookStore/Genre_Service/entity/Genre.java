package com.bookStore.Genre_Service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "genres")
public class Genre {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String description;



}
