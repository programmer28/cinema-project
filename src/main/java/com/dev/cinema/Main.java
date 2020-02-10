package com.dev.cinema;

import com.dev.cinema.dao.ShoppingCartDao;
import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.lib.Injector;
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
import java.util.List;

public class Main {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        movieService.getAll().forEach(System.out::println);

        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        movie = movieService.add(movie);

        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall = cinemaHallService.add(cinemaHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(movie);
        movieSession.setShowTime(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(19,30)));
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);

        List<MovieSession> availaibleSessions = movieSessionService
                .findAvailableSessions(movie.getId(), LocalDate.now());
        availaibleSessions.forEach(System.out::println);

        String email = "yaroslav28@i.ua";
        String password = "1234";

        //Register the user
        AuthenticationService authenticationService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);
        authenticationService.register(email, password);

        //Get user who is buying the tickets
        User user = null;
        try {
            user = authenticationService.login(email, password);
        } catch (AuthenticationException e) {
            System.out.println("Wrong email or password" + e);
        }

        UserService userService = (UserService)
                injector.getInstance(UserService.class);
        userService.findByEmail("yaroslav28@i.ua");

        //Add a movie session to the shopping cart
        ShoppingCartService bucketService = (ShoppingCartService)
                injector.getInstance(ShoppingCartService.class);
        MovieSession selectedMovieSession = availaibleSessions.get(0);
        bucketService.addSession(selectedMovieSession, user);
        //ShoppingCart userBucket = bucketService.getByUser(user);

        User user2 = authenticationService.register("i@i.ua", "pass");

        //Check for existing tickets in shopping cart
        ShoppingCartDao shoppingCartDao = (ShoppingCartDao)
                injector.getInstance(ShoppingCartDao.class);
        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user);
        shoppingCart.getTickets().forEach(System.out::println);

        //Check for completing order
        OrderService orderService = (OrderService)
                injector.getInstance(OrderService.class);
        System.out.println(shoppingCart);
        Order order = orderService.completeOrder(shoppingCart);
        System.out.println(order);
        System.out.println(shoppingCart);

        //Check for getting list of all orders for single user
        orderService.getOrderHistory(user).forEach(System.out::println);
    }

}
