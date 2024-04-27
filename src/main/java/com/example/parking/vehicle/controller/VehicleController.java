package com.example.parking.vehicle.controller;

import com.example.parking.vehicle.dto.VehicleFullDto;
import com.example.parking.vehicle.dto.VehicleUpdateDto;
import com.example.parking.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Validated
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    public VehicleFullDto create(@RequestBody @Valid VehicleFullDto vehicle, Authentication authentication) {
        return vehicleService.create(vehicle, authentication.getName());
    }

    @PatchMapping("/{vehicleID}")
    public VehicleFullDto update(@PathVariable @NotNull Long vehicleID, @RequestBody VehicleUpdateDto vehicle) {
        return vehicleService.update(vehicleID, vehicle);
    }

    @GetMapping("/{vehicleID}")
    public VehicleFullDto getData(@PathVariable @NotNull Long vehicleID) {
        return vehicleService.getData(vehicleID);
    }

}
