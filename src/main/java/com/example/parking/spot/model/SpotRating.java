package com.example.parking.spot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "spots_rating")
@AllArgsConstructor
@NoArgsConstructor
public class SpotRating {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name="spot_id", nullable = false)
    private Long spotId;
    @Column(name="user_id", nullable = false)
    private Long userId;
    @Column(name="user_name", nullable = false)
    private String userName;
    private Integer rating;
    private String comment;
    private LocalDateTime dateTime;
}