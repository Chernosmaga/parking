package com.example.parking.vehicle.service;


import com.example.parking.enums.RequestState;
import com.example.parking.exception.DataAccessException;
import com.example.parking.exception.DataViolationException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.user.model.User;
import com.example.parking.user.repository.UserRepository;
import com.example.parking.vehicle.dto.TransferRequestCreateDto;
import com.example.parking.vehicle.dto.TransferRequestDto;
import com.example.parking.vehicle.dto.VehicleFullDto;
import com.example.parking.vehicle.dto.VehicleUpdateDto;
import com.example.parking.vehicle.mapper.TransferRequestMapper;
import com.example.parking.vehicle.mapper.VehicleMapper;
import com.example.parking.vehicle.model.TransferRequest;
import com.example.parking.vehicle.model.Vehicle;
import com.example.parking.vehicle.repository.TransferRequestRepository;
import com.example.parking.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.parking.enums.RequestState.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final UserRepository userRepository;
    private final TransferRequestMapper transferRequestMapper;
    private final TransferRequestRepository transferRequestRepository;

    @Override
    public VehicleFullDto create(VehicleFullDto vehicle, String userPhone) {
        User user = findUser(userPhone);
        if (vehicleRepository.existsByGovNumberAndVin(vehicle.getGovNumber(), vehicle.getVin())) {
            throw new DataIntegrityViolationException("The vehicle with the specified registration number or " +
                    "VIN has already been registered");
        }
        Vehicle newVehicle = vehicleMapper.toVehicle(vehicle);
        newVehicle.setOwner(user);
        Vehicle saved = vehicleRepository.save(newVehicle);
        user.getVehicles().add(saved);
        userRepository.save(user);
        log.info("The vehicle has been successfully created: {}", saved);
        return vehicleMapper.toVehicleFullDto(saved);
    }

    @Override
    public VehicleFullDto update(Long vehicleID, VehicleUpdateDto vehicle, String userPhone) {
        User user = findUser(userPhone);
        Vehicle found = getVehicleByIdFromRepository(vehicleID);
        if (!found.getOwner().equals(user)) {
            throw new DataAccessException("Not enough rights to perform this operation");
        }
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

    @Override
    public TransferRequestDto vehicleTransfer(TransferRequestCreateDto request, String userPhone) {
        User requester = findUser(userPhone);
        User owner = userRepository
                .findByPhoneAndNameAndSurname(request.phoneNumber(), request.name(), request.surname());
        Vehicle vehicle = vehicleRepository.findByGovNumberAndVin(request.govNumber(), request.vin());
        if (vehicle == null) {
            throw new NotFoundException("The vehicle data hasn't been found");
        }
        if (!vehicle.getOwner().equals(owner)) {
            throw new DataViolationException("Incorrect vehicle owner data");
        }
        if (transferRequestRepository.existsByOwnerAndRequester(owner, requester)) {
            throw new DataViolationException("Unable to create already existing request");
        }
        TransferRequest saved = transferRequestRepository
                .save(new TransferRequest(null, owner, vehicle, requester, LocalDateTime.now(), PENDING));
        log.info("New transfer request has been created: {}", saved);
        return transferRequestMapper.toTransferRequestDto(saved);
    }

    @Override
    public TransferRequestDto vehicleTransferConfirmation(Long requestId, RequestState state, String userPhone) {
        User user = findUser(userPhone);
        TransferRequest request = transferRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Transfer request hasn't been found"));
        if (!request.getVehicle().getOwner().equals(user)) {
            throw new DataAccessException("You have no rights to perform this operation");
        }
        if (ACCEPTED.equals(state)) {
            User requester = request.getRequester();
            Set<Vehicle> vehicles = requester.getVehicles();
            vehicles.add(request.getVehicle());
            userRepository.save(requester);
            request.setState(ACCEPTED);
        } else {
            request.setState(REJECTED);
        }
        TransferRequest saved = transferRequestRepository.save(request);
        log.info("Transfer request has been {}:{}", saved.getState(), saved);
        return transferRequestMapper.toTransferRequestDto(saved);
    }

    @Override
    public List<TransferRequestDto> getTransferRequestsByUser(RequestState state, String userPhone,
                                                              int from, int size, String sort) {
        User requester = findUser(userPhone);
        Sort.Direction direction = Sort.Direction.fromString(sort);
        PageRequest page = PageRequest.of(from, size, Sort.by(direction, "date"));
        List<TransferRequest> requests = transferRequestRepository.findAllByRequesterAndState(requester, state, page)
                .getContent();
        log.info("User has returned the list of transfer requests: {}", requests);
        return requests.stream().map(transferRequestMapper::toTransferRequestDto).collect(Collectors.toList());
    }

    @Override
    public List<TransferRequestDto> getTransferRequestsByOwner(RequestState state, String userPhone,
                                                               int from, int size, String sort) {
        User owner = findUser(userPhone);
        Sort.Direction direction = Sort.Direction.fromString(sort);
        PageRequest page = PageRequest.of(from, size, Sort.by(direction, "date"));
        List<TransferRequest> requests = transferRequestRepository.findAllByOwnerAndState(owner, state, page)
                .getContent();
        log.info("Owner has returned the list of transfer requests: {}", requests);
        return requests.stream().map(transferRequestMapper::toTransferRequestDto).collect(Collectors.toList());
    }

    private Vehicle getVehicleByIdFromRepository(Long vehicleID) {
        return vehicleRepository.findById(vehicleID).orElseThrow(() -> new NotFoundException("The vehicle wasn't found"));
    }

    private User findUser(String userPhone) {
        return userRepository.findByPhone(userPhone).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
