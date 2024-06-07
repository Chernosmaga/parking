package com.example.parking.spot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSpotRequestDto {
    @NotNull
    private BigDecimal latitude;
    @NotNull
    private BigDecimal longitude;
    @NotNull
    private Boolean isStoreyed;
    @NotNull
    private Short floor;
    private String picture; //TODO: a picture value can be null, but not the empty string
    @NotNull
    private Boolean isHandicapped;
}