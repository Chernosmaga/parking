package com.example.parking.spot.controller;

import com.example.parking.spot.dto.SpotReviewsFullDto;
import com.example.parking.spot.dto.SpotReviewsGetSpotDto;
import com.example.parking.spot.dto.SpotReviewsGetUserDto;
import com.example.parking.spot.service.SpotReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/personal/spots")
@RequiredArgsConstructor
public class PersonalSpotReviewsController {
    private final SpotReviewsService spotReviewsService;

    @PostMapping(path = "/reviews/{spotId}")
    public SpotReviewsFullDto createRating(@RequestBody @Valid SpotReviewsFullDto spotRating, Authentication authentication, @PathVariable Long spotId) {
        return spotReviewsService.createReview(spotRating, authentication.getName(), spotId);
    }

    @DeleteMapping(path = "/reviews/{spotId}")
    public SpotReviewsFullDto deleteRating(Authentication authentication, @PathVariable Long spotId) {
        return spotReviewsService.deleteSpotReview(authentication.getName(), spotId);
    }

    @GetMapping(path = "/reviews/{spotId}")
    public SpotReviewsGetSpotDto getSpotRating(@PathVariable Long spotId,
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "10") int size) {
        return spotReviewsService.getSpotReviews(spotId, from, size);
    }

    @GetMapping("/reviews/my_reviews")
    public SpotReviewsGetUserDto getMyReviews(Authentication authentication,
                                              @RequestParam(defaultValue = "0") int from,
                                              @RequestParam(defaultValue = "10") int size) {
        return spotReviewsService.getMyReviews(authentication.getName(), from, size);
    }

}