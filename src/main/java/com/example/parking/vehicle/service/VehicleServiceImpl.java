package com.example.parking.vehicle.service;


import com.example.parking.exception.NotFoundException;
import com.example.parking.vehicle.dto.VehicleFullDto;
import com.example.parking.vehicle.dto.VehicleUpdateDto;
import com.example.parking.vehicle.mapper.VehicleMapper;
import com.example.parking.vehicle.model.Vehicle;
import com.example.parking.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public VehicleFullDto create(VehicleFullDto vehicle) {
        /*
        if (vehicleRepository.existsByGovNumberOrVin(vehicle.getGovNumber(), vehicle.getVin())) {
            Vehicle existingVehicle = vehicleMapper.toVehicle(vehicle);
            log.info("Транспортное средство уже присутствует в Базе: {}", existingVehicle);
            return vehicleMapper.toVehicleFullDto(existingVehicle);
        }*/
        Vehicle newVehicle = vehicleMapper.toVehicle(vehicle);
        Vehicle saved = vehicleRepository.save(newVehicle);
        log.info("Транспортное средство успешно добавлено: {}", saved);
        return vehicleMapper.toVehicleFullDto(saved);
    }

    @Override
    public VehicleFullDto update(Long vehicleID, VehicleUpdateDto vehicle) {
        Vehicle found = vehicleRepository.findById(vehicleID).orElseThrow(() -> new NotFoundException("Транспортное средство не найдено"));
        found.setColor(vehicle.getColor() != null ? vehicle.getColor() : found.getColor());
        found.setVin(vehicle.getGovNumber() != null ? vehicle.getGovNumber() : found.getGovNumber());

        Vehicle saved = vehicleRepository.save(found);
        log.info("Транспортное средство успешно обновлено: {}", saved);

        return vehicleMapper.toVehicleFullDto(saved);
    }

    @Override
    public VehicleFullDto getData(Long vehicleID) {
        Vehicle vehicle = vehicleRepository.findById(vehicleID).orElseThrow(() -> new NotFoundException("Транспортное средство не найдено"));
        log.info("Пользователь запросил данные своего транспортного средства {}", vehicle);
        return vehicleMapper.toVehicleFullDto(vehicle);
    }
}
