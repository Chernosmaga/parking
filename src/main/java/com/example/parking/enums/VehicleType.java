package com.example.parking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum VehicleType {
    CAR ("Легковой автомобиль"),
    TRUCK("Грузовик"),
    MOTORBIKE("Мотоцикл"),
    BUS("Автобус");

    private final String title;
}
