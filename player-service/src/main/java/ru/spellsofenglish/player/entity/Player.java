package ru.spellsofenglish.player.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    private String username;

    @Min(value = 0L, message = "The minimum value of points is 0")
    private Integer points;

/*
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Progress progress;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Settings settings;*/

}
