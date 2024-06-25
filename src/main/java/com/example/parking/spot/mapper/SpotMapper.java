package com.example.parking.spot.mapper;

import com.example.parking.spot.dto.NewSpotRequestDto;
import com.example.parking.spot.dto.SpotMainResponseDto;
import com.example.parking.spot.dto.SpotWithRatingResponseDto;
import com.example.parking.spot.model.Spot;
import com.example.parking.spot.model.SpotReviews;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotMapper {
    Spot toSpot(NewSpotRequestDto newSpotRequestDto);

    SpotMainResponseDto toSpotMainResponseDto(Spot spot);

    SpotWithRatingResponseDto toSpotWithRatingResponseDto(Spot spot, List<SpotReviews> ratings);

}