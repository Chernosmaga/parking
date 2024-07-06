package com.example.parking.search.service;

import com.example.parking.enums.SortType;
import com.example.parking.search.dto.LocationDto;
import com.example.parking.search.dto.SpotNearbyDto;

import java.util.List;

public interface SearchService {
    List<SpotNearbyDto> findNearest(String phone, LocationDto location, SortType type, int from, int size);
}
