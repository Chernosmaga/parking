package com.example.parking.search.controller;

import com.example.parking.enums.SortType;
import com.example.parking.search.dto.LocationDto;
import com.example.parking.search.dto.SpotNearbyDto;
import com.example.parking.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping()
    public List<SpotNearbyDto> getNearby(Authentication authentication, @RequestBody LocationDto locationDto,
                                         @RequestParam(defaultValue = "NEAREST") SortType sort,
                                         @RequestParam(defaultValue = "0") int from,
                                         @RequestParam(defaultValue = "10") int size) {
        return searchService.findNearest(authentication.getName(), locationDto, sort, from, size);
    }
}
