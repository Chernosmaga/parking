package com.example.parking.spot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotReviewsShortDto {
    private Long id;
    private Integer rating;
    private String comment;
}