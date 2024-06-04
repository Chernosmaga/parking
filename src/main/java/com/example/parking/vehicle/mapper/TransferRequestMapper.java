package com.example.parking.vehicle.mapper;

import com.example.parking.trade.model.TradeRequest;
import com.example.parking.vehicle.dto.TransferRequestDto;
import com.example.parking.vehicle.model.TransferRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferRequestMapper {
    TransferRequestDto toTransferRequestDto(TransferRequest request);
}
