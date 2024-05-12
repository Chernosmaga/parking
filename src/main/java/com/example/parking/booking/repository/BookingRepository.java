package com.example.parking.booking.repository;

import com.example.parking.booking.model.Booking;
import com.example.parking.spot.model.Spot;
import com.example.parking.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Booking entity
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByBookerAndSpot(User booker, Spot spot);

    boolean existsBySpot(Spot spot);
}
