package com.example.parking.user.model;

import com.example.parking.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
