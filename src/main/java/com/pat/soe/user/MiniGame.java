package com.pat.soe.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Table(name = "MiniGame") // this the table has not yet been created
public class MiniGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "user_id")
    private int user_id; //fk

    @Column(name = "LanguageLevel")
    private int languageLevel;

    @Column(name = "field_id")
    private int field_id; //fk

    @Column(name = "dice")
    private int dice;

    @Column(name = "field_id")
    private int task_id; //fk

    @Column(name = "score")
    private int score;
}
