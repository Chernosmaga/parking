package com.example.parking.spot.dto;

import com.example.parking.spot.model.SpotState;
import com.example.parking.spot.model.SpotReviews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotWithRatingResponseDto {
    private Long id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private SpotState spotState;
    private Boolean isStoreyed;
    private Short floor;
    private String picture;
    private Boolean isHandicapped;
    private List<SpotReviews> ratings;
    private Double averageRating;
}