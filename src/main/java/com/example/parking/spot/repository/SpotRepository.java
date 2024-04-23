package com.example.parking.spot.repository;

import com.example.parking.spot.model.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
    Boolean existsByLatitudeAndLongitudeAndFloor(BigDecimal latitude, BigDecimal longitude, Short floor);
}