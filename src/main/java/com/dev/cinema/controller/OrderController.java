package com.dev.cinema.controller;

import com.dev.cinema.dto.OrderResponseDto;
import com.dev.cinema.dto.ShoppingCartResponseDto;
import com.dev.cinema.dto.UserResponseDto;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/complete")
    public void complete(@RequestBody ShoppingCartResponseDto shoppingCartResponseDto) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setTickets(shoppingCartResponseDto.getTickets());
        shoppingCart.setUser(shoppingCartResponseDto.getUser());
        orderService.completeOrder(shoppingCart);
    }

    @PostMapping("/")
    public List<OrderResponseDto> getAll(@RequestBody UserResponseDto userResponseDto) {
        User user = userService.findByEmail(userResponseDto.getEmail());
        return orderService.getOrderHistory(user)
                .stream()
                .map(this::getOrderResponseDto)
                .collect(Collectors.toList());
    }

    private OrderResponseDto getOrderResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setTickets(order.getTickets());
        dto.setUser(order.getUser());
        dto.setOrderDate(order.getOrderDate());
        return dto;
    }
}
