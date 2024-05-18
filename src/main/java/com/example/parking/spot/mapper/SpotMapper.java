package com.example.parking.spot.mapper;

import com.example.parking.spot.dto.NewSpotRequestDto;
import com.example.parking.spot.dto.SpotMainResponseDto;
import com.example.parking.spot.dto.SpotRatingResponseDto;
import com.example.parking.spot.model.Spot;
import com.example.parking.spot.model.SpotRating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpotMapper {
    Spot toSpot(NewSpotRequestDto newSpotRequestDto);

    SpotMainResponseDto toSpotMainResponseDto(Spot spot);

    SpotRating toSpotRating(SpotRatingResponseDto spotRatingResponseDto);

    SpotRatingResponseDto toSpotRatingResponseDto(SpotRating spotRating);


}