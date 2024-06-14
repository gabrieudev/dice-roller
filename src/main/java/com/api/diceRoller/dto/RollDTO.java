package com.api.diceRoller.dto;

import com.api.diceRoller.model.enums.DiceType;
import jakarta.validation.constraints.NotNull;
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
    private int quantity;

    @NotNull
    private DiceType DiceType;

    @NotNull
    private int modifier;

    @NotNull
    private boolean advantage;

    @NotNull
    private boolean disadvantage;

    private int result;

    private Instant timestamp;

}
