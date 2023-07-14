package ru.spellsofenglish.gameservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public record PlayerDto(
        UUID id,
        String username
) {
}
