package com.example.parking.vehicle.service;


import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.user.model.User;
import com.example.parking.user.repository.UserRepository;
import com.example.parking.vehicle.dto.VehicleFullDto;
import com.example.parking.vehicle.dto.VehicleUpdateDto;
import com.example.parking.vehicle.mapper.VehicleMapper;
import com.example.parking.vehicle.model.Vehicle;
import com.example.parking.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final UserRepository userRepository;

    @Override
    public VehicleFullDto create(VehicleFullDto vehicle, String userPhone) {

        User user = userRepository.findByPhone(userPhone).orElseThrow(() -> new NotFoundException("User not found"));

        // Если одно ТС юзают несеолько юзеров, то берем из репозитория уже существующую запись
        Optional<Vehicle> existingVehicle = vehicleRepository.findByGovNumberAndVin(vehicle.getGovNumber(), vehicle.getVin());

        if (existingVehicle.isPresent()) {
            user.getVehicles().add(existingVehicle.get());
            userRepository.save(user);
            log.info("The vehicle is already present in the Database: {}", existingVehicle.get());

            return vehicleMapper.toVehicleFullDto(existingVehicle.get());
        }

        Vehicle newVehicle = vehicleMapper.toVehicle(vehicle);
        try {
            Vehicle saved = vehicleRepository.save(newVehicle);
            user.getVehicles().add(saved);
            userRepository.save(user);
            log.info("The vehicle has been successfully created: {}", saved);

            return vehicleMapper.toVehicleFullDto(saved);

        } catch (DataIntegrityViolationException exception) {
            throw new AlreadyExistsException("The vehicle with the specified registration number or VIN has already been registered");
        }
    }

    @Override
    public VehicleFullDto update(Long vehicleID, VehicleUpdateDto vehicle) {
        Vehicle found = getVehicleByIdFromRepository(vehicleID);
        found.setColor(vehicle.getColor() != null ? vehicle.getColor() : found.getColor());
        found.setGovNumber(vehicle.getGovNumber() != null ? vehicle.getGovNumber() : found.getGovNumber());

        Vehicle saved = vehicleRepository.save(found);
        log.info("Vehicle information has been successfully updated: {}", saved);

        return vehicleMapper.toVehicleFullDto(saved);
    }

    @Override
    public VehicleFullDto getData(Long vehicleID) {
        Vehicle vehicle = getVehicleByIdFromRepository(vehicleID);
        log.info("The user requested the data of his vehicle: {}", vehicle);
        return vehicleMapper.toVehicleFullDto(vehicle);
    }

    // Метод поиска ТС на гос. или VIN номерам
    private Vehicle getVehicleByIdFromRepository(Long vehicleID) {
        return vehicleRepository.findById(vehicleID).orElseThrow(() -> new NotFoundException("The vehicle wasn't found"));
    }
}
