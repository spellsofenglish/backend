package ru.spellsofenglish.player.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(unique = true, nullable = false)
    private UUID id;

    @JoinColumn(unique = true)
    private String username;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Progress progress;
}
