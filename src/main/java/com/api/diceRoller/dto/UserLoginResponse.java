package com.api.diceRoller.dto;

public record UserLoginResponse(String token, Long expiresIn) {
}
