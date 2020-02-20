package com.dev.cinema.controller;

import com.dev.cinema.dto.MovieSessionResponseDto;
import com.dev.cinema.dto.ShoppingCartResponseDto;
import com.dev.cinema.dto.UserResponseDto;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingcarts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    @GetMapping("/byuser")
    public ShoppingCartResponseDto getByUser(UserResponseDto userResponseDto) {
        User user = userService.findByEmail(userResponseDto.getEmail());
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        return getShoppingCartResponseDto(shoppingCart);
    }

    @PostMapping("/addmoviesession")
    public void addMovieSession(@RequestBody MovieSessionResponseDto movieSessionResponseDto,
                                @RequestBody UserResponseDto userResponseDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieSessionResponseDto.getMovie());
        movieSession.setCinemaHall(movieSessionResponseDto.getCinemaHall());
        movieSession.setShowTime(movieSessionResponseDto.getShowTime());
        User user = userService.findByEmail(userResponseDto.getEmail());
        shoppingCartService.addSession(movieSession, user);
    }

    private ShoppingCartResponseDto getShoppingCartResponseDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto dto = new ShoppingCartResponseDto();
        dto.setTickets(shoppingCart.getTickets());
        dto.setUser(shoppingCart.getUser());
        return dto;
    }
}
