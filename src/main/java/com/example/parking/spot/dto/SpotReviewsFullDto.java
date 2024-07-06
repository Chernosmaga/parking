package com.example.parking.spot.dto;

import com.example.parking.user.dto.UserShortDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotReviewsFullDto {
    private Long id;
    private UserShortDto user;
    @NotNull
    @Min(value = 1, message = "Rating should not be less than 1")
    @Max(value = 5, message = "Rating should not be greater than 5")
    private Integer rating;
    private String comment;
    private String operationStatus;
}