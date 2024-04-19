package com.example.parking.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleFullDto {
    private Long id;
    @NotBlank
    private String model;
    @NotBlank
    private String type;
    @NotBlank
    private String vin;
    @Past
    @NotBlank
    private String releaseYear;
    @NotBlank
    private String color;
    @NotBlank
    private String engineType;
    @NotBlank
    private String govNumber;
}
