package com.example.parking;

import com.example.parking.vehicle.dto.VehicleFullDto;
import com.example.parking.vehicle.dto.VehicleShortDto;
import com.example.parking.vehicle.dto.VehicleUpdateDto;
import com.example.parking.vehicle.repository.VehicleRepository;
import com.example.parking.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.example.parking.enums.VehicleType.CAR;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class VehicleServiceImplTest {
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;

    private final VehicleFullDto carFull = new VehicleFullDto(null, CAR.toString(), "Type", "testvin", LocalDate.of(1997, 10, 10), "Черный", "Паровой двигатель", "У199ХК96");
    private final VehicleUpdateDto updateDto = new VehicleUpdateDto(null, "Белый", null);

    private final VehicleShortDto shortDto = new VehicleShortDto();
}
