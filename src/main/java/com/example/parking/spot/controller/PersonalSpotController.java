package com.example.parking.spot.controller;

import com.example.parking.spot.dto.SpotMainResponseDto;
import com.example.parking.spot.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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