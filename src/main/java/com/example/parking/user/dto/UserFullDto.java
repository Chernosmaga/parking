package com.example.parking.user.dto;

import com.example.parking.annotation.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Past
    @NotBlank
    private LocalDate birthdate;
    @Email
    @NotBlank
    private String email;
    @Phone
    @NotBlank
    private String phone;
    @NotBlank
    @Length(min = 8, max = 32)
    private String password;
}
