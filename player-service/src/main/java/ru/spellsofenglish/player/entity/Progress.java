package ru.spellsofenglish.player.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "progress")
@Accessors(chain = true)
@Builder
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Min(value = 0)
    private Long progress;

    @Min(value = 0)
    private Long gameLevel;
}
