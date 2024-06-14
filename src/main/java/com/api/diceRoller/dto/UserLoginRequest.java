package com.api.diceRoller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password

) {
}
