package com.example.parking.spot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "spots")
@AllArgsConstructor
@NoArgsConstructor
public class Spot {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    @Column(name = "spot_state", nullable = false)
    @Enumerated(STRING)
    private SpotState spotState;
    @Column(name = "is_storeyed", nullable = false)
    private Boolean isStoreyed;
    private Short floor;
    private String picture;
    @Column(name = "is_handicapped", nullable = false)
    private Boolean isHandicapped;
}