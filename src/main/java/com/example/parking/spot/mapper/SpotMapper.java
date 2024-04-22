package com.example.parking.spot.mapper;

import com.example.parking.spot.dto.SpotFullDto;
import com.example.parking.spot.model.Spot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpotMapper {
    Spot toSpot(SpotFullDto spotFullDto);

    SpotFullDto toSpotFullDto(Spot spot);
}
