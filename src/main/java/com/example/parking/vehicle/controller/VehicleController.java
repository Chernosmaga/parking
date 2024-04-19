package com.example.parking.vehicle.controller;

import com.example.parking.vehicle.dto.VehicleFullDto;
import com.example.parking.vehicle.dto.VehicleUpdateDto;
import com.example.parking.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public VehicleFullDto create(@RequestBody VehicleFullDto vehicle) {
        return vehicleService.create(vehicle);
    }

    @PatchMapping("/{vehicleID}")
    public VehicleFullDto update(@PathVariable Long vehicleID, @RequestBody VehicleUpdateDto vehicle) {
        return vehicleService.update(vehicleID, vehicle);
    }

    @GetMapping("/{vehicleID}")
    public VehicleFullDto getData(@PathVariable Long vehicleID) {
        return vehicleService.getData(vehicleID);
    }

}
