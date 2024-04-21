package com.example.parking.vehicle.mapper;

import com.example.parking.vehicle.dto.VehicleFullDto;
import com.example.parking.vehicle.model.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    Vehicle toVehicle(VehicleFullDto vehicle);

    VehicleFullDto toVehicleFullDto (Vehicle vehicle);

}
