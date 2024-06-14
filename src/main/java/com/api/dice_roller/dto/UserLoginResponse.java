package com.api.dice_roller.dto;

public record UserLoginResponse(String token, Long expiresIn) {
}
