package com.example.parking.spot.dto;

import com.example.parking.spot.model.SpotState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotMainResponseDto {
    private Long id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private SpotState spotState;
    private Boolean isStoreyed;
    private Short floor;
    private String picture;
    private Boolean isHandicapped;
}