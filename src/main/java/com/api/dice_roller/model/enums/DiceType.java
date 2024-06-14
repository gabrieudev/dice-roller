package com.api.dice_roller.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DiceType {
    d2("d2"),
    d4("d4"),
    d6("d6"),
    d8("d8"),
    d10("d10"),
    d12("d12"),
    d20("d20"),
    d100("d100");

    private String type;
}
