package com.example.parking.spot.repository;

import com.example.parking.spot.model.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
    Boolean existsByLatitudeAndLongitudeAndFloor(BigDecimal latitude, BigDecimal longitude, Short floor);

    Boolean existsByLatitudeAndLongitudeAndFloorAndIdNot(BigDecimal latitude, BigDecimal longitude, Short floor, Long spotId);

    @Query(value = "select * from spots order by (6371 * acos(cos(radians(:userLat)) * " +
            "cos(radians(latitude)) * cos(radians(longitude) - radians(:userLon)) + sin(radians(:userLat)) * " +
            "sin(radians(latitude)))) offset :page * :size", nativeQuery = true)
    List<Spot> findNearestSpots(@Param("userLat") BigDecimal userLat, @Param("userLon") BigDecimal userLon,
                                @Param("page") int page, @Param("size") int size);
}