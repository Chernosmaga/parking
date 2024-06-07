package com.example.parking.spot.service;

import com.example.parking.spot.dto.NewSpotRequestDto;
import com.example.parking.spot.dto.SpotMainResponseDto;
import com.example.parking.spot.dto.UpdateSpotRequestDto;

public interface SpotService {
    SpotMainResponseDto create(NewSpotRequestDto newSpotRequestDto);

    SpotMainResponseDto update(Long spotId, UpdateSpotRequestDto updateSpotRequestDto);

    SpotMainResponseDto getData(Long spotId);
}
