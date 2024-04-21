package com.example.parking.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Dto for creation of an entity of spot booking
 */
@Data
@AllArgsConstructor
public class BookingCreateDto {
    @NotBlank
    private String spot;
    @Future
    @NotBlank
    private LocalDateTime end;
    @NotBlank
    private Boolean isTradable;
    @NotBlank
    private Boolean isSubscription;
}