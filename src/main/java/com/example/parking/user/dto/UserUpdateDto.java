package com.example.parking.user.dto;

import com.example.parking.annotation.Phone;
import jakarta.validation.constraints.*;
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
    @NotEmpty
    @Size(min = 2)
    @Pattern(regexp = "^[^0-9]*$")
    private String name;
    @NotEmpty
    @Size(min = 2)
    @Pattern(regexp = "^[^0-9]*$")
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
