package com.example.parking.trade.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record CreateTradeRequestDto(
        @NotNull
        @Positive
        BigDecimal price,
        @NotBlank
        @Length(min = 5, max = 50)
        String comment
) {}
