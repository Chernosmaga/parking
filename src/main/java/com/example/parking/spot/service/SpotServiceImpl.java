package com.example.parking.spot.service;

import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.spot.dto.NewSpotRequestDto;
import com.example.parking.spot.dto.SpotMainResponseDto;
import com.example.parking.spot.dto.UpdateSpotRequestDto;
import com.example.parking.spot.mapper.SpotMapper;
import com.example.parking.spot.model.Spot;
import com.example.parking.spot.model.SpotState;
import com.example.parking.spot.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {
    private final SpotRepository spotRepository;
    private final SpotMapper spotMapper;

    public SpotMainResponseDto create(NewSpotRequestDto newSpotRequestDto) {
        throwExceptionIfSpotExist(newSpotRequestDto.getLatitude(), newSpotRequestDto.getLongitude(), newSpotRequestDto.getFloor());

        Spot spotToSave = spotMapper.toSpot(newSpotRequestDto);
        spotToSave.setSpotState(SpotState.BLOCKED);
        Spot savedSpot = spotRepository.save(spotToSave);
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

        throwExceptionIfSpotExist(latitude, longitude, floor);

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
        return spotMapper.toSpotMainResponseDto(updatedSpot);
    }

    public SpotMainResponseDto getData(Long spotId) {
        Spot foundSpot = findSpotOrThrowException(spotId);
        return spotMapper.toSpotMainResponseDto(foundSpot);
    }

    private void throwExceptionIfSpotExist(BigDecimal latitude, BigDecimal longitude, short floor) {
        if (spotRepository.existsByLatitudeAndLongitudeAndFloor(latitude, longitude, floor)) {
            throw new AlreadyExistsException("Spot with specified latitude, longitude, floor already exists");
        }
    }

    private Spot findSpotOrThrowException(Long spotId) {
        return spotRepository.findById(spotId).orElseThrow(() -> new NotFoundException("Spot with id " + spotId + " hasn't been found"));
    }
}