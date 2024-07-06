package com.example.parking.search.service;

import com.example.parking.enums.SortType;
import com.example.parking.exception.NotFoundException;
import com.example.parking.search.dto.LocationDto;
import com.example.parking.search.dto.SpotNearbyDto;
import com.example.parking.search.mapper.SpotNearbyMapper;
import com.example.parking.spot.model.Spot;
import com.example.parking.spot.repository.SpotRepository;
import com.example.parking.user.model.User;
import com.example.parking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final UserRepository userRepository;
    private final SpotRepository spotRepository;
    private final SpotNearbyMapper spotNearbyMapper;

    @Override
    public List<SpotNearbyDto> findNearest(String phone, LocationDto location, SortType sortType, int from, int size) {
        findUser(phone);
        List<Spot> spots = spotRepository.findNearestSpots(location.latitude(), location.longitude(), from, size);
        List<SpotNearbyDto> nearbySpots = switch (sortType) {
            case NEAREST -> spots.stream().map(spotNearbyMapper::toSpotNearbyDto).collect(Collectors.toList());
            case RATING -> spots.stream().map(spotNearbyMapper::toSpotNearbyDto)
                    .sorted(Comparator.comparing(SpotNearbyDto::rating)).collect(Collectors.toList());
            case BOOKING -> spots.stream().map(spotNearbyMapper::toSpotNearbyDto)
                    .sorted(Comparator.comparing(SpotNearbyDto::endOfBooking)).collect(Collectors.toList());
            case TRADING -> spots.stream().map(spotNearbyMapper::toSpotNearbyDto)
                    .sorted(Comparator.comparing(SpotNearbyDto::isTradable)).collect(Collectors.toList());
        };
        log.info("User has returned the list of nearby spots: {}", nearbySpots);
        return nearbySpots;
    }

    private void findUser(String phone) {
        Optional<User> user = userRepository.findByPhone(phone);
        if (user.isEmpty()) {
            throw new NotFoundException("User data wasn't found");
        }
    }
}
