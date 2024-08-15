package com.bookstore.Book_Service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Service
@FeignClient(name = "${genre-service.name}", url = "${genre.url}")
public interface GenreClient {

    @GetMapping("getGenreByName/{name}")
    ResponseEntity<Map<String, Object>> getGenreByName(@PathVariable("name") String name);

    }
