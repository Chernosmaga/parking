package com.example.parking.booking.mapper;


import com.example.parking.booking.dto.BookingCreateDto;
import com.example.parking.booking.dto.BookingFullDto;
import com.example.parking.booking.model.Booking;
import org.mapstruct.Mapper;

/**
 * Mapper for Booking entity
 */
@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingFullDto toBookingFullDto(Booking booking);

    Booking toBooking(BookingCreateDto booking);
}
