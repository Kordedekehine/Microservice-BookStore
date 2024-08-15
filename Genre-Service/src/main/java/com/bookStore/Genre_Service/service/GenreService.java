package com.bookStore.Genre_Service.service;


import com.bookStore.Genre_Service.dtos.GenreDto;
import com.bookStore.Genre_Service.dtos.GenreResponseDto;
import com.bookStore.Genre_Service.exception.AlreadyExistsException;
import com.bookStore.Genre_Service.exception.NotFoundException;

import java.util.List;

public interface GenreService {


    GenreResponseDto saveGenre(GenreDto genreDto) throws AlreadyExistsException;

    GenreResponseDto updateGenre(Long id, GenreDto genreDto) throws NotFoundException;

    List<GenreResponseDto> listAllGenres();

    GenreResponseDto getGenreByName(String name) throws NotFoundException;

    void deleteGenre(Long id) throws NotFoundException;
}
