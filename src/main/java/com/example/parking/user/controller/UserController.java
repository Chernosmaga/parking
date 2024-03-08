package com.example.parking.user.controller;

import com.example.parking.user.dto.UserFullDto;
import com.example.parking.user.dto.UserUpdateDto;
import com.example.parking.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserFullDto create(@RequestBody UserFullDto user) {
        return userService.create(user);
    }

    @PutMapping("/{userId}")
    public UserFullDto update(@PathVariable Long userId, @RequestBody UserUpdateDto user) {
        return userService.update(userId, user);
    }

    @GetMapping("/{userId}")
    public UserFullDto getData(@PathVariable Long userId) {
        return userService.getData(userId);
    }
}
