package com.example.parking.booking.service;

import com.example.parking.booking.dto.BookingCreateDto;
import com.example.parking.booking.dto.BookingFullDto;
import com.example.parking.booking.mapper.BookingMapper;
import com.example.parking.booking.model.Booking;
import com.example.parking.booking.repository.BookingRepository;
import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.user.model.User;
import com.example.parking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementation of BookingService's interface
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingFullDto create(Long userId, BookingCreateDto booking) {
        User user = findUser(userId);
        if (bookingRepository.existsBySpot("Dummy")) {
            throw new AlreadyExistsException("The spot is already taken");
        }
        Booking thisBooking = bookingMapper.toBooking(booking);
        thisBooking.setBooker(user);
        thisBooking.setSpot("Dummy");
        thisBooking.setStart(LocalDateTime.now());
        thisBooking.setIsPaid(false);
        Booking saved = bookingRepository.save(thisBooking);
        log.info("Booking of spot: {}", saved);
        return bookingMapper.toBookingFullDto(saved);
    }

    @Override
    public BookingFullDto pay(Long userId, Long bookingId) {
//        TODO create PaymentService for money transaction
        return null;
    }

    @Override
    public void cancel(Long userId, Long bookingId) {
        findUser(userId);
        Booking booking = findBooking(bookingId);
        log.info("User attempted to cancel booking: {}", booking);
        bookingRepository.delete(booking);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User's data wasn't found"));
    }

    private Booking findBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking's data wasn't found"));
    }
}
