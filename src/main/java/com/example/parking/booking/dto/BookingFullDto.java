package com.example.parking.booking.dto;

import com.example.parking.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * Record class for returning data of spot booking
 */
public record BookingFullDto(Long id, UserShortDto booker, String spot, LocalDateTime start, LocalDateTime end,
                             Boolean isTradable, Boolean isPaid, String bill, Boolean isSubscription) {}
