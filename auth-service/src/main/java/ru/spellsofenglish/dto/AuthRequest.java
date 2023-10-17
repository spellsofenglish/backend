package ru.spellsofenglish.dto;

public record AuthRequest(
        String username,
        String password
) {
}
