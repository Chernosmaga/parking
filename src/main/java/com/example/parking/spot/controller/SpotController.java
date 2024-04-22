package com.example.parking.spot.controller;

import com.example.parking.spot.dto.SpotFullDto;
import com.example.parking.spot.dto.SpotUpdateDto;
import com.example.parking.spot.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/private/spots")
@RequiredArgsConstructor
public class SpotController {
    private final SpotService spotService;

    @PostMapping
    public SpotFullDto create(@RequestBody SpotFullDto spotFullDto) {
        return spotService.create(spotFullDto);
    }

    @PutMapping("/{spotId}")
    public SpotFullDto update(@PathVariable Long spotId, @RequestBody SpotUpdateDto spotUpdateDto) {
        return spotService.update(spotId, spotUpdateDto);
    }

    @GetMapping("/{spotId}")
    public SpotFullDto getData(@PathVariable Long spotId) {
        return spotService.getData(spotId);
    }
}