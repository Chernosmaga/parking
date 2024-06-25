package com.example.parking.spot.service;

import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.spot.dto.NewSpotRequestDto;
import com.example.parking.spot.dto.SpotMainResponseDto;
import com.example.parking.spot.dto.SpotWithRatingResponseDto;
import com.example.parking.spot.dto.UpdateSpotRequestDto;
import com.example.parking.spot.mapper.SpotMapper;
import com.example.parking.spot.model.Spot;
import com.example.parking.spot.model.SpotReviews;
import com.example.parking.spot.model.SpotState;
import com.example.parking.spot.repository.SpotReviewsRepository;
import com.example.parking.spot.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {
    private final SpotRepository spotRepository;
    private final SpotMapper spotMapper;
    private final SpotReviewsRepository spotReviewsRepository;

    public SpotMainResponseDto create(NewSpotRequestDto newSpotRequestDto) {
        throwExceptionIfSpotExist(newSpotRequestDto.getLatitude(), newSpotRequestDto.getLongitude(), newSpotRequestDto.getFloor());

        Spot spotToSave = spotMapper.toSpot(newSpotRequestDto);
        spotToSave.setSpotState(SpotState.BLOCKED);
        spotToSave.setAverageRating(0.0);
        Spot savedSpot = spotRepository.save(spotToSave);
        log.info("An administrator has created a new parking spot : {}", savedSpot);
        return spotMapper.toSpotMainResponseDto(savedSpot);
    }

    public SpotMainResponseDto update(Long spotId, UpdateSpotRequestDto updateSpotRequestDto) {

        Spot spotOriginal = findSpotOrThrowException(spotId);

        boolean isLatitudeInDto = updateSpotRequestDto.getLatitude() != null;
        boolean isLongitudeInDto = updateSpotRequestDto.getLongitude() != null;
        boolean isFloorInDto = updateSpotRequestDto.getFloor() != null;
        BigDecimal latitude = isLatitudeInDto ? updateSpotRequestDto.getLatitude() : spotOriginal.getLatitude();
        BigDecimal longitude = isLongitudeInDto ? updateSpotRequestDto.getLongitude() : spotOriginal.getLongitude();
        short floor = isFloorInDto ? updateSpotRequestDto.getFloor() : spotOriginal.getFloor();

        if (isLatitudeInDto || isLongitudeInDto || isFloorInDto) {
            throwExceptionIfSpotExist(latitude, longitude, floor, spotId);
        }

        if (isLatitudeInDto) {
            spotOriginal.setLatitude(latitude);
        }

        if (isLongitudeInDto) {
            spotOriginal.setLongitude(longitude);
        }

        if (updateSpotRequestDto.getIsStoreyed() != null) {
            spotOriginal.setIsStoreyed(updateSpotRequestDto.getIsStoreyed());
        }

        if (isFloorInDto) {
            spotOriginal.setFloor(floor);
        }

        if (updateSpotRequestDto.getPicture() != null) {
            spotOriginal.setPicture(updateSpotRequestDto.getPicture());
        }

        if (updateSpotRequestDto.getIsHandicapped() != null) {
            spotOriginal.setIsHandicapped(updateSpotRequestDto.getIsHandicapped());
        }

        Spot updatedSpot = spotRepository.save(spotOriginal);
        log.info("An administrator has updated the parking spot {} : {}", spotId, updatedSpot);
        return spotMapper.toSpotMainResponseDto(updatedSpot);
    }

    @Override
    public SpotWithRatingResponseDto getData(Long spotId) {
        // Нужно вынести отзывы в отдельный метод
        Spot foundSpot = findSpotOrThrowException(spotId);
        List<SpotReviews> ratings = spotReviewsRepository.findBySpotId(spotId);
        Double averageRating = ratings.stream()
                .mapToInt(SpotReviews::getRating)
                .average()
                .orElse(0.0);
        foundSpot.setAverageRating(averageRating);
        log.info("An user has got the parking spot data {} : {}", spotId, foundSpot);
        return spotMapper.toSpotWithRatingResponseDto(foundSpot, ratings);
    }

    private void throwExceptionIfSpotExist(BigDecimal latitude, BigDecimal longitude, short floor) {
        if (spotRepository.existsByLatitudeAndLongitudeAndFloor(latitude, longitude, floor)) {
            throw new AlreadyExistsException("Spot with specified latitude, longitude, floor already exists");
        }
    }

    private void throwExceptionIfSpotExist(BigDecimal latitude, BigDecimal longitude, short floor, Long spotId) {
        if (spotRepository.existsByLatitudeAndLongitudeAndFloorAndIdNot(latitude, longitude, floor, spotId)) {
            throw new AlreadyExistsException("Spot with specified latitude, longitude, floor and spotId already exists");
        }
    }

    private Spot findSpotOrThrowException(Long spotId) {
        return spotRepository.findById(spotId).orElseThrow(() -> new NotFoundException("Spot with id " + spotId + " hasn't been found"));
    }
}