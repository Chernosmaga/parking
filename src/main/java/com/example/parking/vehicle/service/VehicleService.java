package com.example.parking.vehicle.service;

import com.example.parking.enums.RequestState;
import com.example.parking.vehicle.dto.TransferRequestCreateDto;
import com.example.parking.vehicle.dto.TransferRequestDto;
import com.example.parking.vehicle.dto.VehicleFullDto;
import com.example.parking.vehicle.dto.VehicleUpdateDto;

import java.util.List;


public interface VehicleService {
    /**
     * Creation of a new vehicle for user
     * @param vehicle new vehicle dto
     * @param userPhone user's data
     * @return new vehicle with user's data
     */
    VehicleFullDto create(VehicleFullDto vehicle, String userPhone);

    /**
     * Update method for vehicle data
     * @param vehicleID the vehicle's identifier
     * @param vehicle new vehicle data for update
     * @param userPhone user's data
     * @return updated dto of vehicle
     */
    VehicleFullDto update(Long vehicleID, VehicleUpdateDto vehicle, String userPhone);

    /**
     * Get full information of a vehicle by identifier
     * @param vehicleID vehicle's identifier
     * @return return particular vehicle's data
     */
    VehicleFullDto getData(Long vehicleID);

    /**
     * The creation of a request for adding vehicle information to another user
     * @param request the information of an owner of the vehicle and his vehicle
     * @param userPhone requester data
     * @return transfer request dto with transfer data and status
     */
    TransferRequestDto vehicleTransfer(TransferRequestCreateDto request, String userPhone);

    /**
     * Confirmation of transfer request by new owner of the vehicle
     * @param requestId the identifier of the needed request
     * @param state of the transfer request either accepted or rejected
     * @param userPhone the data of a new owner of the vehicle
     * @return transfer request dto with transfer data and status
     */
    TransferRequestDto vehicleTransferConfirmation(Long requestId, RequestState state, String userPhone);

    /**
     * The request to get the list of outgoing requests for vehicle transfer
     * @param state the state of the request
     * @param userPhone authentication of the user by phone number
     * @param from start page
     * @param size end page
     * @param sort sort for date field ascending or descending
     * @return the list of requests sorted and chosen by particular parameters
     */
    List<TransferRequestDto> getTransferRequestsByUser(RequestState state, String userPhone,
                                                       int from, int size, String sort);

    /**
     * The request to get the list of incoming requests for vehicle transfer
     * @param state the state of the request
     * @param userPhone authentication of the owner of the vehicle by phone number
     * @param from start page
     * @param size end page
     * @param sort sort for date field ascending or descending
     * @return the list of requests sorted and chosen by particular parameters
     */
    List<TransferRequestDto> getTransferRequestsByOwner(RequestState state, String userPhone,
                                                        int from, int size, String sort);
}
