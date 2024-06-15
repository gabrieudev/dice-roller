package com.api.diceRoller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class RollDTO {

    private Long id;

    @NotNull
    private UserDTO user;

    @NotNull
    @Positive
    private int quantity;

    @NotNull
    @Positive
    private int sides;

    @NotNull
    private int modifier;

    @NotNull
    private boolean advantage;

    @NotNull
    private boolean disadvantage;

    private int result;

    private Instant timestamp;

}
