package com.api.diceRoller.dto;

import java.util.List;

public record ResultDTO(List<Integer> result, int modifier, int total) {
}
