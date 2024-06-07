package com.example.parking.booking.repository;

import com.example.parking.booking.model.Booking;
import com.example.parking.spot.model.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Booking entity
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsBySpot(Spot spot);

    Page<Booking> findBookingBySpot(Spot spot, Pageable page);
}
