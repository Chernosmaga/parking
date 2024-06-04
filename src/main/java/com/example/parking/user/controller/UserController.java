package com.example.parking.user.controller;

import com.example.parking.user.dto.UserFullDto;
import com.example.parking.user.dto.UserUpdateDto;
import com.example.parking.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PatchMapping("/personal/users/{userId}")
    public UserFullDto update(@PathVariable @NotNull Long userId,
                              @RequestBody @Valid UserUpdateDto user,
                              Authentication authentication) {
        return userService.update(userId, user, authentication.getName());
    }

    @GetMapping("/personal/users/{userId}")
    public UserFullDto getData(@PathVariable @NotNull Long userId) {
        return userService.getData(userId);
    }
}
