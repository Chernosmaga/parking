package com.example.parking.vehicle.dto;

import com.example.parking.enums.RequestState;
import com.example.parking.user.dto.UserShortDto;

import java.time.LocalDateTime;

public record TransferRequestDto(
        Long id,
        UserShortDto owner,
        VehicleFullDto vehicle,
        UserShortDto requester,
        LocalDateTime date,
        RequestState state)
{}
