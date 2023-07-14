package ru.spellsofenglish.gameservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Progress {
    private UUID id;
    private Double progress;
    private Integer gameLevel;
    private Integer totalPoints;
}
