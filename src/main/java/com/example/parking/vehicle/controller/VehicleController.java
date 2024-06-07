package com.example.parking.vehicle.controller;

import com.example.parking.enums.RequestState;
import com.example.parking.vehicle.dto.TransferRequestCreateDto;
import com.example.parking.vehicle.dto.TransferRequestDto;
import com.example.parking.vehicle.dto.VehicleFullDto;
import com.example.parking.vehicle.dto.VehicleUpdateDto;
import com.example.parking.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Validated
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping
    @ResponseStatus(CREATED)
    public VehicleFullDto create(@RequestBody @Valid VehicleFullDto vehicle, Authentication authentication) {
        return vehicleService.create(vehicle, authentication.getName());
    }

    @PatchMapping("/{vehicleID}")
    public VehicleFullDto update(@PathVariable @NotNull Long vehicleID,
                                 @RequestBody VehicleUpdateDto vehicle,
                                 Authentication authentication) {
        return vehicleService.update(vehicleID, vehicle, authentication.getName());
    }

    @GetMapping("/{vehicleID}")
    public VehicleFullDto getData(@PathVariable @NotNull Long vehicleID) {
        return vehicleService.getData(vehicleID);
    }

    @PostMapping("/transfer")
    @ResponseStatus(CREATED)
    public TransferRequestDto vehicleTransfer(@RequestBody @Valid TransferRequestCreateDto request,
                                              Authentication authentication) {
        return vehicleService.vehicleTransfer(request, authentication.getName());
    }

    @PatchMapping("/transfer/{requestId}")
    public TransferRequestDto vehicleTransferConfirmation(@PathVariable @NotNull Long requestId,
                                                          @RequestParam(defaultValue = "PENDING") RequestState state,
                                                          Authentication authentication) {
        return vehicleService.vehicleTransferConfirmation(requestId, state, authentication.getName());
    }

    @GetMapping("/requester/transfer")
    public List<TransferRequestDto> getTransferRequestsByUser(@RequestParam(defaultValue = "PENDING") RequestState state,
                                                              @RequestParam(defaultValue = "0") int from,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "DESC") String sort,
                                                              Authentication authentication) {
        return vehicleService.getTransferRequestsByUser(state, authentication.getName(), from, size, sort);
    }

    @GetMapping("/owner/transfer")
    public List<TransferRequestDto> getTransferRequestsByOwner(@RequestParam(defaultValue = "PENDING") RequestState state,
                                                               @RequestParam(defaultValue = "0") int from,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(defaultValue = "DESC") String sort,
                                                               Authentication authentication) {
        return vehicleService.getTransferRequestsByOwner(state, authentication.getName(), from, size, sort);
    }
}