package com.example.parking.booking.controller;

import com.example.parking.booking.dto.BookingCreateDto;
import com.example.parking.booking.dto.BookingFullDto;
import com.example.parking.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Booking controller receives data
 */
@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/personal/bookings/{userId}")
    @ResponseStatus(CREATED)
    public BookingFullDto create(@PathVariable Long userId, @RequestBody BookingCreateDto booking) {
        return bookingService.create(userId, booking);
    }

    @DeleteMapping("/personal/bookings/{userId}/{bookingId}")
    @ResponseStatus(NO_CONTENT)
    public void cancel(@PathVariable Long userId, @PathVariable Long bookingId) {
        bookingService.cancel(userId, bookingId);
    }
}