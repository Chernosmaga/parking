package com.example.parking.user.mapper;

import com.example.parking.user.dto.UserFullDto;
import com.example.parking.user.dto.UserShortDto;
import com.example.parking.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserFullDto user);

    UserShortDto toUserShortDto(User user);

    UserFullDto toUserFullDto(User user);
}
