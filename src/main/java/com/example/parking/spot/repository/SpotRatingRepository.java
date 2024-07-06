package com.example.parking.spot.repository;

import com.example.parking.spot.model.SpotRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpotRatingRepository extends JpaRepository<SpotRating, Long> {
    Optional<SpotRating> findByUserIdAndSpotId(Long userId, Long spotId);
    List<SpotRating> findBySpotId(Long spotId);
}
