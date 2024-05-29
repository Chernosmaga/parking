package com.example.parking.vehicle.dto;

import com.example.parking.annotation.Phone;
import jakarta.validation.constraints.NotBlank;

public record TransferRequestCreateDto(
        @NotBlank(message = "Field name cannot be empty")  String name,
        @NotBlank(message = "Field surname mustn't be empty") String surname,
        @NotBlank @Phone(message = "Phone number doesn't match pattern") String phoneNumber,
        @NotBlank(message = "Empty data for vin field is not allowed") String vin,
        @NotBlank(message = "Unable to find vehicle because govNumber field is empty") String govNumber)
{}
