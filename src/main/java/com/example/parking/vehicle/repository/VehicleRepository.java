package com.example.parking.vehicle.repository;

import com.example.parking.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByGovNumberAndVin(String govNumber, String vin);

    Optional<Vehicle> findByGovNumberAndVin(String govNumber, String vin);
}