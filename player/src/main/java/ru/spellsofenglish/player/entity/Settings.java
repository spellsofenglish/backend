package ru.spellsofenglish.player.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/*@Entity*/
public class Settings {

    private Boolean hasAudio;

    @Enumerated(EnumType.STRING)
    private Language gameLanguage;
}
