package com.example.parking;

import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.vehicle.dto.VehicleFullDto;
import com.example.parking.vehicle.dto.VehicleUpdateDto;
import com.example.parking.vehicle.repository.VehicleRepository;
import com.example.parking.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.parking.enums.VehicleType.CAR;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class VehicleServiceImplTest {
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;

    private final VehicleFullDto car1 = new VehicleFullDto(null, "Форд Фокус 1" , CAR.toString(), "testvin", "2005", "Черный", "Паровой двигатель", "У199ХК96");
    private final VehicleUpdateDto carUpdate = new VehicleUpdateDto(null, "Белый", "Т228УК77");
    private final VehicleFullDto car2 = new VehicleFullDto(null, "Рено Логан", CAR.toString(), "testvin2", "2017", "Желтый", "Дизель", "Х777ХХ777");

    @AfterEach
    void afterEch() {
        vehicleRepository.deleteAll();
    }

    @Test
    void create_shouldCreateUser() {
        VehicleFullDto created = vehicleService.create(car1);

        assertNotNull(created);
        assertEquals(car1.getModel(), created.getModel());
        assertEquals(car1.getType(), created.getType());
        assertEquals(car1.getVin(), created.getVin());
        assertEquals(car1.getReleaseYear(), created.getReleaseYear());
        assertEquals(car1.getColor(), created.getColor());
        assertEquals(car1.getEngineType(), created.getEngineType());
        assertEquals(car1.getGovNumber(), created.getGovNumber());
    }

    @Test
    void create_shouldThrowExceptionIfColorExists() {
        vehicleService.create(car1);
        car2.setColor(car1.getColor());

        assertThrows(AlreadyExistsException.class,
                () -> vehicleService.create(car2));
    }

    @Test
    void create_shouldThrowExceptionIfGovNumberExists() {
        vehicleService.create(car1);
        car2.setColor(car1.getGovNumber());

        assertThrows(AlreadyExistsException.class,
                () -> vehicleService.create(car2));
    }

    @Test
    void update_shouldUpdateAllFields() {
        VehicleFullDto created = vehicleService.create(car1);
        VehicleFullDto updated = vehicleService.update(created.getId(), carUpdate);

        assertEquals(carUpdate.getColor(), updated.getColor());
        assertEquals(carUpdate.getGovNumber(), updated.getGovNumber());
    }

    @Test
    void getData_shouldReturnVehicleData() {
        VehicleFullDto created = vehicleService.create(car1);
        VehicleFullDto returned = vehicleService.getData(created.getId());

        assertEquals(created.getGovNumber(), returned.getGovNumber());
        assertEquals(created.getVin(), returned.getVin());
    }

    @Test
    void getData_shouldThrowExceptionIfNotExists() {
        assertThrows(NotFoundException.class ,
                () -> vehicleService.getData(999L));
    }
}
