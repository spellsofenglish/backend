package ru.spellsofenglish.player.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @OneToOne
    private Player player;

    @Enumerated(EnumType.STRING)
    private Level languageLevel;

    @Min(value = 0, message = "Progress cannot be less than 0 or null")
    private Byte progress;

    @Min(value = 0, message = "Game level cannot be less than 0 or null")
    private Long gameLevel;

}
