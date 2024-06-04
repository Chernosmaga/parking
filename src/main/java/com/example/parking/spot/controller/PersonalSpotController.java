package com.example.parking.spot.controller;

import com.example.parking.spot.dto.SpotWithRatingResponseDto;
import com.example.parking.spot.service.SpotRatingService;
import org.springframework.security.core.Authentication;
import com.example.parking.spot.dto.SpotMainResponseDto;
import com.example.parking.spot.dto.SpotRatingResponseDto;
import com.example.parking.spot.service.SpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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