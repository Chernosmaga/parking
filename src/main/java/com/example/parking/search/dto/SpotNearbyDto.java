package com.example.parking.search.dto;

import com.example.parking.spot.model.SpotState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;

public record SpotNearbyDto (Long id, BigDecimal latitude, BigDecimal longitude,
                            SpotState spotState, String picture, Boolean isHandicapped,
                            Boolean isTradable, LocalDateTime endOfBooking, Double rating) implements Comparable<SpotNearbyDto> {
    @Override
    public int compareTo(SpotNearbyDto other) {
        if (other == null) return 1;
        if (this.isTradable == null || other.isTradable == null) return 0;
        if (this.endOfBooking == null || other.endOfBooking == null) return 0;
        if (this.rating == null ||other.rating == null) return 0;
        return Comparator.comparing(SpotNearbyDto::isTradable, Comparator.nullsLast(Boolean::compareTo))
                .thenComparing(SpotNearbyDto::endOfBooking, Comparator.nullsLast(LocalDateTime::compareTo))
                .thenComparingDouble(SpotNearbyDto::rating)
                .compare(this, other);
    }
}
