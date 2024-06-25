package com.example.parking.spot.dto;

import com.example.parking.user.dto.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotReviewsGetUserDto {
    private int currentPage;
    private int totalPages;
    private int numberOfItems;
    private long totalItems;
    private UserShortDto user;
    private List<SpotReviewsShortDto> reviews;
}