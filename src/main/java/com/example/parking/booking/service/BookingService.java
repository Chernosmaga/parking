package com.example.parking.booking.service;

import com.example.parking.booking.dto.BookingCreateDto;
import com.example.parking.booking.dto.BookingFullDto;

/**
 * Booking's service interface with basic methods for Booking entity
 */
public interface BookingService {
    /**
     * Creates spot's booking by particular user
     * @param userId booker's identifier
     * @param booking the entity of booking
     * @return the full dto of booking with user's data
     */
    BookingFullDto create(Long userId, BookingCreateDto booking);

    /**
     * Method for money transaction for booking
     * @param userId user's identifier
     * @param bookingId booking's identifier
     * @return return full dto of booking with bill
     */
    BookingFullDto pay(Long userId, Long bookingId);

    /**
     * Booking cancelling method
     * @param userId user's identifier
     * @param bookingId booking's identifier
     */
    void cancel(Long userId, Long bookingId);
}
