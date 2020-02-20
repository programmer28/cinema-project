package com.dev.cinema.controller;

import com.dev.cinema.dto.MovieResponseDto;
import com.dev.cinema.model.Movie;
import com.dev.cinema.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public void add(@RequestBody MovieResponseDto movieResponseDto) {
        Movie movie = new Movie();
        movie.setTitle(movieResponseDto.getTitle());
        movie.setDescription(movieResponseDto.getDescription());
        movieService.add(movie);
    }

    @GetMapping
    public List<MovieResponseDto> getAll() {
        return movieService.getAll()
                .stream()
                .map(this::getMovieResponseDto)
                .collect(Collectors.toList());
    }

    private MovieResponseDto getMovieResponseDto(Movie movie) {
        MovieResponseDto dto = new MovieResponseDto();
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        return dto;
    }
}
