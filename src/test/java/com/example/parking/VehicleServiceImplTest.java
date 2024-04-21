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

import java.time.LocalDate;

import static com.example.parking.enums.VehicleType.CAR;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class VehicleServiceImplTest {
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;

    private final VehicleFullDto car1 = new VehicleFullDto(null, "Форд Фокус 1" , CAR.toString(), "testvin",  LocalDate.of(2005, 1, 1), "Black", "Паровой двигатель", "У199ХК96");
    private final VehicleUpdateDto carUpdate = new VehicleUpdateDto(null, "White", "Т228УК77");
    private final VehicleFullDto car2 = new VehicleFullDto(null, "Рено Логан", CAR.toString(), "testvin2", LocalDate.of(2017, 1, 1), "Yellow", "Дизель", "Х777ХХ777");

    @AfterEach
    void afterEch() {
        vehicleRepository.deleteAll();
    }

    @Test
    void create_shouldCreateVehicle() {
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

    // Не уверен, что корректно написал этот тест
    @Test
    void create_shouldThrowExceptionIfRequiredFieldsAreEmpty() {
        VehicleFullDto created = vehicleService.create(car1);
        created.setModel(null);
        created.setType(null);
        created.setVin(null);
        created.setReleaseYear(null);

        created.setColor(null);
        created.setEngineType(null);
        created.setGovNumber(null);

        assertNotNull(created);
        assertThrows(AlreadyExistsException.class,
                () -> vehicleService.create(created));
    }

    @Test
    void create_shouldThrowExceptionIfReleaseYearIsInFuture() {
        VehicleFullDto created = vehicleService.create(car1);
        created.setReleaseYear(LocalDate.of(3024, 1, 1));

        assertNotNull(created);
        assertThrows(AlreadyExistsException.class,
                () -> vehicleService.create(created));
    }

    @Test
    void create_shouldThrowExceptionIfGovNumberExists() {
        vehicleService.create(car1);
        car2.setGovNumber(car1.getGovNumber());

        assertThrows(AlreadyExistsException.class,
                () -> vehicleService.create(car2));
    }

    @Test
    void create_shouldThrowExceptionIfVinExists() {
        vehicleService.create(car1);
        car2.setVin(car1.getVin());

        Exception exception = assertThrows(AlreadyExistsException.class,
                () -> vehicleService.create(car2));

        assertEquals(exception.getMessage(),
                "The vehicle with the specified registration number or VIN has already been registered");

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
    void getData_shouldThrowExceptionIfVehicleIDNotExists() {
        Exception exception = assertThrows(NotFoundException.class ,
                () -> vehicleService.getData(999L));

        assertEquals(exception.getMessage(),
                "The vehicle wasn't found");

    }
}