package com.example.parking.vehicle.model;

import com.example.parking.enums.VehicleType;
import com.example.parking.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "vehicles")
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private String model;
    @Enumerated(STRING)
    private VehicleType type;
    private String vin;
    @Column(name = "release_year")
    private LocalDate releaseYear;
    private String color;
    @Column(name = "engine_type")
    private String engineType;
    @Column(name = "gov_number")
    private String govNumber;
}