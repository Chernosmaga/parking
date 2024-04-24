package com.example.parking.spot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSpotRequestDto {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean isStoreyed;
    private Short floor;
    private String picture;
    private Boolean isHandicapped;
}