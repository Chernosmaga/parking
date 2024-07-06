package com.example.parking.spot.mapper;

import com.example.parking.spot.dto.SpotReviewsFullDto;
import com.example.parking.spot.dto.SpotReviewsShortDto;
import com.example.parking.spot.dto.UserReviewDto;
import com.example.parking.spot.model.SpotReviews;
import com.example.parking.user.dto.UserShortDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SpotReviewsMapper {
    SpotReviews toSpotReviews(SpotReviewsFullDto spotReviewsFullDto);
    SpotReviewsFullDto toSpotReviewsFullDto(SpotReviews spotReviews);
    SpotReviewsShortDto toSpotReviewsShortDto(SpotReviews spotReviews);
    @Mapping(source = "user.id", target = "user.id")
    @Mapping(source = "user.name", target = "user.name")
    @Mapping(source = "user.surname", target = "user.surname")
    @Mapping(source = "spotReviews", target = "review")
    UserReviewDto toUserReviewDto(UserShortDto user, SpotReviews spotReviews);
}