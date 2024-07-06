package com.example.parking.spot.mapper;

import com.example.parking.spot.dto.SpotRatingResponseDto;
import com.example.parking.spot.model.SpotRating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpotRatingMapper {
    SpotRating toSpotRating(SpotRatingResponseDto spotRatingResponseDto);
    SpotRatingResponseDto toSpotRatingResponseDto(SpotRating spotRating);


}