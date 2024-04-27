package com.example.parking.user.model;

import com.example.parking.enums.State;
import com.example.parking.vehicle.model.Vehicle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthdate;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    private String email;
    private String phone;
    private String password;
    @Enumerated(STRING)
    private State state;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToMany
    @JoinTable(name = "users_vehicles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id", referencedColumnName = "id"))
    private Set<Vehicle> vehicles;
}
