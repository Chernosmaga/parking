package com.example.parking.booking.dto;

import com.example.parking.spot.dto.SpotMainResponseDto;
import com.example.parking.user.dto.UserShortDto;
import com.example.parking.vehicle.dto.VehicleFullDto;

import java.time.LocalDateTime;

/**
 * Record class for returning data of spot booking
 */
public record BookingFullDto(Long id, UserShortDto booker, SpotMainResponseDto spot, VehicleFullDto vehicle, LocalDateTime start, LocalDateTime end,
                             Boolean isTradable, Boolean isPaid, String bill, Boolean isSubscription) {}
