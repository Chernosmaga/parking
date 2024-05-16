package com.example.parking.spot.controller;

import com.example.parking.spot.dto.SpotMainResponseDto;
import com.example.parking.spot.service.SpotService;
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

    @GetMapping(path =  "/{spotId}")
    public SpotMainResponseDto getData(@PathVariable Long spotId) {
        return spotService.getData(spotId);
    }
}