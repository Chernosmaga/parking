package com.example.parking.user.dto;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class UserAuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";

    public UserAuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
