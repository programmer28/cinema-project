package com.dev.cinema.controller;

import com.dev.cinema.dto.UserResponseDto;
import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthenticationController {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserResponseDto userResponseDto) {
        try {
            authenticationService.login(userResponseDto.getEmail(),
                    userResponseDto.getPassword());
        } catch (AuthenticationException e) {
            LOGGER.error(e);
            return e.getMessage();
        }
        return "User is logged in successfully";
    }

    @PostMapping("/register")
    public String register(@RequestBody UserResponseDto userResponseDto) {
        try {
            authenticationService.register(userResponseDto.getEmail(),
                    userResponseDto.getPassword());
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            return e.getMessage();
        }
        return "User is registered successfully";
    }
}
