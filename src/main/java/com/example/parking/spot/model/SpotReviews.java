package com.example.parking.spot.model;

import com.example.parking.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "spots_reviews")
@AllArgsConstructor
@NoArgsConstructor
public class SpotReviews {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="spot_id")
    private Spot spot;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Integer rating;
    private String comment;
    private LocalDateTime dateTime;
}