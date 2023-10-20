package ru.spellsofenglish.dto;

import java.util.UUID;

public record AuthRequest(
        UUID userId,
        String username,
        String password
) {
}
