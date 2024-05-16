package com.example.parking.trade.dto;

import com.example.parking.enums.RequestState;
import com.example.parking.spot.model.Spot;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TradeRequestFullDto(Long id, BigDecimal price, Spot spot, LocalDateTime requestDate,
                                  RequestState requestState, String comment) {
}
