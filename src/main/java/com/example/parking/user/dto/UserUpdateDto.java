package com.example.parking.user.dto;

import com.example.parking.annotation.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private Long id;
    private String name;
    private String surname;
    @Past
    private LocalDate birthdate;
    @Email
    private String email;
    @Phone
    private String phone;
    @Length(min = 8, max = 32)
    private String password;
}
