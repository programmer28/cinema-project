package com.dev.cinema.controller;

import com.dev.cinema.dto.UserResponseDto;
import com.dev.cinema.model.User;
import com.dev.cinema.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public void add(@RequestBody UserResponseDto userResponseDto) {
        User user = new User();
        user.setEmail(userResponseDto.getEmail());
        user.setPassword(userResponseDto.getPassword());
        userService.add(user);
    }

    @GetMapping("/byemail")
    public User getUserByEmail(String email) {
        return userService.findByEmail(email);
    }
}
