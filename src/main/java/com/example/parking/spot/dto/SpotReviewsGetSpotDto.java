package com.example.parking.spot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotReviewsGetSpotDto {
    private int currentPage;
    private int totalPages;
    private int numberOfItems;
    private long totalItems;
    private List<UserReviewDto> reviews;
}