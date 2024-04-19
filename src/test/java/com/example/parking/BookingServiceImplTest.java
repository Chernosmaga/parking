package com.example.parking;

import com.example.parking.booking.dto.BookingCreateDto;
import com.example.parking.booking.dto.BookingFullDto;
import com.example.parking.booking.repository.BookingRepository;
import com.example.parking.booking.service.BookingService;
import com.example.parking.exception.AlreadyExistsException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.user.model.User;
import com.example.parking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.parking.enums.State.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceImplTest {
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final UserRepository userRepository;

    private final User first = new User(null, "Pyotr", "Petrov",
            LocalDate.of(1998, 11, 11), LocalDateTime.now().minusDays(10),
            "petrov@mail.ru", "89998885544", "jbsb_jb5GJ3jlnd", ACTIVE);
    private final User second = new User(null, "Alesya", "Ivanova",
            LocalDate.of(1999, 5, 12), LocalDateTime.now().minusDays(20),
            "alesya@mail.ru", "89998881122", "sjbdjgb24bkjb", ACTIVE);
    private final BookingCreateDto bookingCreate = new BookingCreateDto("Dummy",
            LocalDateTime.now().plusDays(10), true, true);

    @AfterEach
    void afterEach() {
        bookingRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void create_shouldCreateBooking() {
        User createdUser = userRepository.save(first);
        BookingFullDto createdBooking = bookingService.create(createdUser.getId(), bookingCreate);

        assertNotNull(createdBooking);
        assertEquals(createdBooking.end(), bookingCreate.getEnd());
    }

    @Test
    void create_shouldThrowExceptionIfUserBookedSpot() {
        User createdUser = userRepository.save(first);
        bookingService.create(createdUser.getId(), bookingCreate);

        assertThrows(AlreadyExistsException.class,
                () -> bookingService.create(createdUser.getId(), bookingCreate));
    }

    @Test
    void create_shouldThrowExceptionIfSpotIsBooked() {
        User createdFirst = userRepository.save(first);
        User createdSecond = userRepository.save(second);
        bookingService.create(createdFirst.getId(), bookingCreate);

        assertThrows(AlreadyExistsException.class,
                () -> bookingService.create(createdSecond.getId(), bookingCreate));
    }

    @Test
    void create_shouldThrowExceptionIfUserNotFound() {
        assertThrows(NotFoundException.class,
                () -> bookingService.create(999L, bookingCreate));
    }

    @Test
    void cancel_shouldCancelBooking() {
        User createdUser = userRepository.save(first);
        BookingFullDto createdBooking = bookingService.create(createdUser.getId(), bookingCreate);
        bookingService.cancel(createdUser.getId(), createdBooking.id());

        assertFalse(bookingRepository.existsByBookerAndSpot(createdUser, "Dummy"));
    }

    @Test
    void cancel_shouldThrowExceptionIfUserNotFound() {
        User createdUser = userRepository.save(first);
        BookingFullDto createdBooking = bookingService.create(createdUser.getId(), bookingCreate);

        assertThrows(NotFoundException.class,
                () -> bookingService.cancel(999L, createdBooking.id()));
    }

    @Test
    void cancel_shouldThrowExceptionIfBookingNotFound() {
        User createdUser = userRepository.save(first);

        assertThrows(NotFoundException.class,
                () -> bookingService.cancel(createdUser.getId(), 999L));
    }
}
