package com.bookStore.Genre_Service.controller;


import com.bookStore.Genre_Service.dtos.GenreDto;
import com.bookStore.Genre_Service.dtos.GenreResponseDto;
import com.bookStore.Genre_Service.exception.AlreadyExistsException;
import com.bookStore.Genre_Service.exception.NotFoundException;
import com.bookStore.Genre_Service.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bookStore.Genre_Service.util.Constants.*;


@RestController
@RequestMapping("/api/v1/genre")
public class GenreController {


    @Autowired
    private GenreService genreService;


    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createGenre(@RequestBody GenreDto genreDto) throws AlreadyExistsException {

        GenreResponseDto saveGenre = genreService.saveGenre(genreDto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.CREATED.value());
        response.put("message", GENRE_CREATED_SUCCESS);
        response.put("data", saveGenre);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("getGenreByName/{name}")
    ResponseEntity<Map<String, Object>> getGenreByName(@PathVariable("name") String name) throws NotFoundException {

        GenreResponseDto genre = genreService.getGenreByName(name);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", GENRE_RETRIEVED_SUCCESS);
        response.put("data", genre);
        return ResponseEntity.ok(response);
    }


    @PutMapping("updateGenre/{id}")
    public ResponseEntity<Map<String, Object>> updateGenreDetails(@PathVariable("id") Long id, @RequestBody GenreDto genreDto) throws NotFoundException {
        GenreResponseDto updateGenre = genreService.updateGenre(id, genreDto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", GENRE_UPDATED_SUCCESS);
        response.put("data", updateGenre);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listGenres")
    public ResponseEntity<Map<String, Object>> getAllGenres() {
        List<GenreResponseDto> genre = genreService.listAllGenres();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", GENRE_RETRIEVED_SUCCESS);
        response.put("data", genre);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("deleteGenre/{id}")
    public ResponseEntity<Map<String, Object>> deleteGenre(@PathVariable("id") Long id) throws NotFoundException {

        genreService.deleteGenre(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.NO_CONTENT.value());
        response.put("message", GENRE_DELETED_SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }

}
