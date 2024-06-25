package com.example.parking.spot.repository;

import com.example.parking.spot.model.SpotReviews;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SpotReviewsRepository extends JpaRepository<SpotReviews, Long> {
    Optional<SpotReviews> findByUserIdAndSpotId(Long userId, Long spotId);
    List<SpotReviews> findBySpotId(Long spotId);
    Page<SpotReviews> findByUserId(Long userId, Pageable page);
    Page<SpotReviews> findBySpotId(Long spotId, Pageable page);
}
