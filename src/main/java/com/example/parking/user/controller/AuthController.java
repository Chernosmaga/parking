package com.example.parking.user.controller;

import com.example.parking.security.JWTGenerator;
import com.example.parking.user.dto.LoginUserDto;
import com.example.parking.user.dto.UserAuthResponseDto;
import com.example.parking.user.dto.UserFullDto;
import com.example.parking.user.dto.UserShortDto;
import com.example.parking.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public UserAuthResponseDto login(@RequestBody LoginUserDto loginUserDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUserDto.getPhone(), loginUserDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new UserAuthResponseDto(token);
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserShortDto register(@RequestBody @Valid UserFullDto userFullDto) {
       return userService.register(userFullDto);
    }
}