package com.example.parking.search.mapper;

import com.example.parking.booking.model.Booking;
import com.example.parking.booking.repository.BookingRepository;
import com.example.parking.search.dto.SpotNearbyDto;
import com.example.parking.spot.model.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SpotNearbyMapper {
    private final BookingRepository bookingRepository;

    public SpotNearbyDto toSpotNearbyDto(Spot spot) {
        Booking booking = bookingRepository.findBySpot(spot);
        return new SpotNearbyDto(
                spot.getId(),
                spot.getLatitude(),
                spot.getLongitude(),
                spot.getSpotState(),
                spot.getPicture(),
                spot.getIsHandicapped(),
                booking != null ? booking.getIsTradable() : true,
                booking != null ? booking.getEnd() : LocalDateTime.now().plusMonths(1),
                spot.getAverageRating());
    }
}
