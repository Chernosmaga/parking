package com.example.parking.search.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record LocationDto(@NotBlank BigDecimal latitude, @NotBlank BigDecimal longitude) {}
