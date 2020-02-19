package com.dev.cinema.controller;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InjectController {
    private final AuthenticationService authenticationService;
    private final CinemaHallService cinemaHallService;
    private final MovieService movieService;
    private final MovieSessionService movieSessionService;
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public InjectController(AuthenticationService authenticationService,
                            CinemaHallService cinemaHallService,
                            MovieService movieService,
                            MovieSessionService movieSessionService,
                            OrderService orderService,
                            ShoppingCartService shoppingCartService,
                            UserService userService) {
        this.authenticationService = authenticationService;
        this.cinemaHallService = cinemaHallService;
        this.movieService = movieService;
        this.movieSessionService = movieSessionService;
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    @GetMapping("/inject")
    public String inject() throws AuthenticationException {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        movieService.add(movie);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(30);
        cinemaHall.setDescription("Green");
        cinemaHallService.add(cinemaHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(movie);
        movieSession.setShowTime(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(17, 00)));
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(1L, LocalDate.now())
                .forEach(System.out::println);

        authenticationService.register("user@i.ua", "abc123");
        User user = authenticationService.login("user@i.ua", "abc123");

        shoppingCartService.addSession(movieSession, user);

        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        Order order = orderService.completeOrder(shoppingCart);
        orderService.getOrderHistory(user).forEach(System.out::println);
        return "inject";
    }
}
