package ru.spellsofenglish.player.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    private String username;

    private Integer points;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Progress progress;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Settings settings;

}
