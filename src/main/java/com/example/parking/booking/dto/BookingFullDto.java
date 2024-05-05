package com.example.parking.booking.dto;

import com.example.parking.spot.model.Spot;
import com.example.parking.spot.model.SpotId;
import com.example.parking.user.dto.UserShortDto;
import com.example.parking.vehicle.model.Vehicle;

import java.time.LocalDateTime;

/**
 * Record class for returning data of spot booking
 */
public record BookingFullDto(Long id, UserShortDto booker, SpotId spot, Vehicle vehicle, LocalDateTime start, LocalDateTime end,
                             Boolean isTradable, Boolean isPaid, String bill, Boolean isSubscription) {}
