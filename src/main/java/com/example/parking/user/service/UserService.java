package com.example.parking.user.service;

import com.example.parking.user.dto.UserFullDto;
import com.example.parking.user.dto.UserShortDto;
import com.example.parking.user.dto.UserUpdateDto;

public interface UserService {
    UserFullDto update(Long userId, UserUpdateDto user);

    UserFullDto getData(Long userId);

    UserShortDto register(UserFullDto user);
}
