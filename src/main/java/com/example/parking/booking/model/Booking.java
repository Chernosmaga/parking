package com.example.parking.booking.model;

import com.example.parking.spot.model.Spot;
import com.example.parking.user.model.User;
import com.example.parking.vehicle.model.Vehicle;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Booking entity which stores at database
 */
@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "booker_id")
    private User booker;
    @ManyToOne
    @JoinColumn(name = "spot_id")
    private Spot spot;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @Column(name = "start_time")
    private LocalDateTime start;
    @Column(name = "end_time")
    private LocalDateTime end;
    @Column(name = "is_tradable")
    private Boolean isTradable;
    @Column(name = "is_paid")
    private Boolean isPaid;
    private String bill;
    @Column(name = "is_subscription")
    private Boolean isSubscription;
}
