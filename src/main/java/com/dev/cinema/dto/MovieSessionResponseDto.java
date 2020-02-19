package com.dev.cinema.dto;

import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import java.time.LocalDateTime;

public class MovieSessionResponseDto {
    private Movie movie;
    private CinemaHall cinemaHall;
    private LocalDateTime showTime;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }
}
