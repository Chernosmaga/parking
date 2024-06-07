package com.example.parking.spot.service;

import com.example.parking.spot.dto.SpotReviewsFullDto;
import com.example.parking.spot.dto.SpotReviewsGetSpotDto;
import com.example.parking.spot.dto.SpotReviewsGetUserDto;

public interface SpotReviewsService {
    /**
     * Create or update a user's review of a spot
     * @param spotRating spot's rating from 1 to 5 and comment
     * @param userPhone user's identifier
     * @param spotId spot's identifier
     * @return the full dto spot rating with the user's data and his review with a rating
     */
    SpotReviewsFullDto createReview(SpotReviewsFullDto spotRating, String userPhone, Long spotId);

    /**
     * Delete a user's review of a spot
     * @param userPhone user's identifier
     * @param spotId spot's identifier
     * @return the short dto spotRatingDeleteDto with the user's data, spots's id and operation status
     */
    SpotReviewsFullDto deleteSpotReview(String userPhone, Long spotId);

    SpotReviewsGetSpotDto getSpotReviews(Long spotId, int from, int size);

    /**
     * Get a list of all user reviews
     * @param userPhone user's identifier
     * @param from the start of the page
     * @param size the end of the page
     * @return info about the user and an array with their reviews
     */
    SpotReviewsGetUserDto getMyReviews(String userPhone, int from, int size);

}
