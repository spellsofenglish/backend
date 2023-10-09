package ru.spellsofenglish.gameservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private UUID id;
    private String username;
    private Progress progress;
    private boolean allowMove;
}

