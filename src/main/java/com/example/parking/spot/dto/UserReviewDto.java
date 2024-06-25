package com.example.parking.spot.dto;

import com.example.parking.user.dto.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReviewDto {
    private UserShortDto user;
    private SpotReviewsShortDto review;
}