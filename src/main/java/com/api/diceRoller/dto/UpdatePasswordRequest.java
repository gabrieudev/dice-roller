package com.api.diceRoller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        String newPassword

) {
}
