package ru.spellsofenglish.player.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @OneToOne
    //TODO fix
    private Player player;

    private Boolean hasAudio;

    @Enumerated(EnumType.STRING)
    private Language gameLanguage;
}
