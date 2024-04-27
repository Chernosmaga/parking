package com.example.parking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//@ToString
public enum VehicleType {
    CAR ("Легковой автомобиль"),
    TRUCK("Грузовик"),
    MOTORBIKE("Мотоцикл"),
    BUS("Автобус");

    private final String title;
}