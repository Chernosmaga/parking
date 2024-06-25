package com.example.parking.booking.service;

import com.example.parking.booking.dto.BookingCreateDto;
import com.example.parking.booking.dto.BookingFullDto;
import com.example.parking.booking.mapper.BookingMapper;
import com.example.parking.booking.model.Booking;
import com.example.parking.booking.repository.BookingRepository;
import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.spot.model.Spot;
import com.example.parking.spot.model.SpotState;
import com.example.parking.spot.repository.SpotRepository;
import com.example.parking.user.model.User;
import com.example.parking.user.repository.UserRepository;
import com.example.parking.vehicle.model.Vehicle;
import com.example.parking.vehicle.repository.VehicleRepository;
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
    private final SpotRepository spotRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public BookingFullDto create(Long userId, Long spotId, Long vehicleId ,BookingCreateDto booking) {
        User user = findUser(userId);
        Spot spot = spotRepository.findById(spotId).orElseThrow( () -> new NotFoundException("Spot's data wasn't found"));
        if (!SpotState.FREE.equals(spot.getSpotState()) ) {
            throw new AlreadyExistsException("The spot isn't FREE");
        } else if (bookingRepository.existsBySpot(spot)) {
            throw new AlreadyExistsException("The spot is already taken");
        }
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new NotFoundException("The vehicle wasn't found"));
        Booking thisBooking = bookingMapper.toBooking(booking);
        thisBooking.setBooker(user);
        thisBooking.setSpot(spot);
        thisBooking.setVehicle(vehicle);
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