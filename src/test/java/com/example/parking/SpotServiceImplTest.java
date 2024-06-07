package com.example.parking;

import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.user.dto.UserFullDto;
import com.example.parking.user.dto.UserUpdateDto;
import com.example.parking.user.repository.UserRepository;
import com.example.parking.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SpotServiceImplTest {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserFullDto ivanov = new UserFullDto(null, "Ivan", "Ivanov",
            LocalDate.of(1997, 10, 10), "ivanov@mail.ru", "89994443322",
            "jsbgb_hk3h4nkngAG");
    private final UserUpdateDto ivanovUpdate = new UserUpdateDto(null, "Artyom", "Sidorov",
            LocalDate.of(1997, 1, 1), "sidorov@mail.ru", "89999991100",
            "sbjkbgj24tbjbkJDJKF");
    private final UserFullDto petrov = new UserFullDto(null, "Pyotr", "Petrov",
            LocalDate.of(1998, 11, 11), "petrov@mail.ru", "89998885544",
            "jbsb_jb5GJ3jlnd");

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void create_shouldCreateUser() {
        UserFullDto created = userService.create(ivanov);

        assertNotNull(created);
        assertEquals(ivanov.getName(), created.getName());
        assertEquals(ivanov.getBirthdate(), created.getBirthdate());
        assertEquals(ivanov.getPhone(), created.getPhone());
        assertEquals(ivanov.getSurname(), created.getSurname());
    }

    @Test
    void create_shouldThrowExceptionIfPhoneExists() {
        userService.create(ivanov);
        petrov.setPhone(ivanov.getPhone());

        assertThrows(AlreadyExistsException.class,
                () -> userService.create(petrov));
    }

    @Test
    void create_shouldThrowExceptionIfEmailExists() {
        userService.create(ivanov);
        petrov.setEmail(ivanov.getEmail());

        assertThrows(AlreadyExistsException.class,
                () -> userService.create(petrov));
    }

    @Test
    void update_shouldUpdateAllFields() {
        UserFullDto created = userService.create(ivanov);
        UserFullDto updated = userService.update(created.getId(), ivanovUpdate);

        assertEquals(ivanovUpdate.getName(), updated.getName());
        assertEquals(ivanovUpdate.getEmail(), updated.getEmail());
        assertEquals(ivanovUpdate.getPassword(), updated.getPassword());
    }

    @Test
    void update_shouldThrowExceptionIfPhoneExists() {
        UserFullDto created = userService.create(ivanov);
        UserUpdateDto dataToUpdate = new UserUpdateDto(null, "Pyotr", "Petrov",
                LocalDate.of(1998, 11, 11), "petrov@mail.ru", created.getPhone(),
                "jbsb_jb5GJ3jlnd");

        assertThrows(AlreadyExistsException.class,
                () -> userService.update(created.getId(), dataToUpdate));
    }

    @Test
    void update_shouldThrowExceptionIfEmailExists() {
        UserFullDto created = userService.create(ivanov);
        UserUpdateDto dataToUpdate = new UserUpdateDto(null, "Pyotr", "Petrov",
                LocalDate.of(1998, 11, 11), created.getEmail(), "89994445577",
                "jbsb_jb5GJ3jlnd");

        assertThrows(AlreadyExistsException.class,
                () -> userService.update(created.getId(), dataToUpdate));
    }

    @Test
    void getData_shouldReturnUserData() {
        UserFullDto created = userService.create(ivanov);
        UserFullDto returned = userService.getData(created.getId());

        assertEquals(created.getName(), returned.getName());
        assertEquals(created.getPassword(), returned.getPassword());
        assertEquals(created.getPhone(), returned.getPhone());
    }

    @Test
    void getData_shouldThrowExceptionIfNotExists() {
        assertThrows(NotFoundException.class,
                () -> userService.getData(999L));
    }
}
