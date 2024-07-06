package com.example.parking.spot.controller;

import com.example.parking.spot.service.SpotRatingService;
import com.example.parking.spot.dto.*;
import com.example.parking.spot.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/personal/spots")
@RequiredArgsConstructor
public class PersonalSpotController {
    private final SpotService spotService;
    private final SpotRatingService spotRatingService;

    @GetMapping(path =  "/{spotId}")
    public SpotWithRatingResponseDto getData(@PathVariable Long spotId) {
        return spotService.getData(spotId);
    }

    @PostMapping(path = "/{spotId}/rating")
    public SpotRatingResponseDto createRating(@RequestBody @Valid SpotRatingResponseDto spotRating, Authentication authentication, @PathVariable Long spotId) {
        return spotRatingService.createRating(spotRating, authentication.getName(), spotId);
    }
}