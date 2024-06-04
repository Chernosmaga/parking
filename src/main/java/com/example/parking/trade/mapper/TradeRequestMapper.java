package com.example.parking.trade.mapper;

import com.example.parking.trade.dto.CreateTradeRequestDto;
import com.example.parking.trade.dto.TradeRequestFullDto;
import com.example.parking.trade.model.TradeRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TradeRequestMapper {
    TradeRequestFullDto toTradeRequestFullDto(TradeRequest tradeRequest);

    TradeRequest toTradeRequest(CreateTradeRequestDto tradeRequest);
}
