package com.example.parking.spot.service;

import com.example.parking.spot.dto.SpotFullDto;
import com.example.parking.spot.dto.SpotUpdateDto;

public interface SpotService {
    SpotFullDto create(SpotFullDto spotFullDto);

    SpotFullDto update(Long spotId, SpotUpdateDto spotUpdateDto);

    SpotFullDto getData(Long spotId);
}
