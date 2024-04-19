package com.example.parking.vehicle.repository;

import com.example.parking.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByGovNumberOrVin(String govNumber, String vin);
}