package com.example.parking.spot.controller;

import com.example.parking.spot.dto.NewSpotRequestDto;
import com.example.parking.spot.dto.SpotMainResponseDto;
import com.example.parking.spot.dto.UpdateSpotRequestDto;
import com.example.parking.spot.service.SpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/spots")
@RequiredArgsConstructor
@Validated
public class AdminSpotController {
    private final SpotService spotService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpotMainResponseDto create(@RequestBody @Valid NewSpotRequestDto newSpotRequestDto) {
        return spotService.create(newSpotRequestDto);
    }

    @PatchMapping(path = "/{spotId}")
    public SpotMainResponseDto update(@PathVariable Long spotId, @RequestBody UpdateSpotRequestDto updateSpotRequestDto) {
        return spotService.update(spotId, updateSpotRequestDto);
    }
}