package com.example.parking.spot.service;

import com.example.parking.spot.dto.SpotRatingResponseDto;
import com.example.parking.spot.model.SpotRating;

public interface SpotRatingService {
    SpotRatingResponseDto createRating(SpotRatingResponseDto spotRating, String userPhone, Long spotId);
}
