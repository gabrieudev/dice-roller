package com.api.diceRoller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PresetDTO {

    private Long id;

    @NotNull
    private UserDTO user;

    @NotBlank
    private String name;

    @NotNull
    private RollDTO roll;

}
