package com.dev.cinema.controller;

import com.dev.cinema.dto.MovieSessionResponseDto;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moviesessions")
public class MovieSessionController {
    private final MovieSessionService movieSessionService;

    public MovieSessionController(MovieSessionService movieSessionService) {
        this.movieSessionService = movieSessionService;
    }

    @PostMapping("/add")
    public void add(@RequestBody MovieSessionResponseDto movieSessionResponseDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieSessionResponseDto.getMovie());
        movieSession.setCinemaHall(movieSessionResponseDto.getCinemaHall());
        movieSession.setShowTime(movieSessionResponseDto.getShowTime());
        movieSessionService.add(movieSession);
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getAll(@RequestParam (name = "movie_id") Long movieId,
                                                @RequestParam (name = "date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return movieSessionService.findAvailableSessions(movieId, localDate)
                .stream()
                .map(this::getMovieSessionResponseDto)
                .collect(Collectors.toList());
    }

    private MovieSessionResponseDto getMovieSessionResponseDto(MovieSession movieSession) {
        MovieSessionResponseDto dto = new MovieSessionResponseDto();
        dto.setMovie(movieSession.getMovie());
        dto.setCinemaHall(movieSession.getCinemaHall());
        dto.setShowTime(movieSession.getShowTime());
        return dto;
    }
}
