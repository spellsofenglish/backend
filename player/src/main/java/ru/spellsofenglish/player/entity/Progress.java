package ru.spellsofenglish.player.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/*@Entity*/
public class Progress {
    @Enumerated(EnumType.STRING)
    private Level languageLevel;
    private Byte progress;
    private Long gameLevel;

}
