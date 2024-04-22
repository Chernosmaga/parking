package com.example.parking.user.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class LoginUserDto {
    private String phone;
    private String password;
}