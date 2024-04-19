package com.example.parking.vehicle.service;

import com.example.parking.vehicle.dto.VehicleFullDto;
import com.example.parking.vehicle.dto.VehicleUpdateDto;


public interface VehicleService {
    VehicleFullDto create(VehicleFullDto vehicle);
    VehicleFullDto update(Long vehicleID, VehicleUpdateDto vehicle);
    VehicleFullDto getData(Long vehicleID);
}
