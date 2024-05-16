package com.example.parking.spot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "spots")
@AllArgsConstructor
@NoArgsConstructor
public class SpotId {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}