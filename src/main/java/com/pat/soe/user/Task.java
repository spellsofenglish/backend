package com.pat.soe.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Table(name = "Task") // this the table has not yet been created
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "time_countdown")
    private int time_countdown; //it's time! (stamp?)

    @Column(name = "description")
    private String description;

    @Column(name = "answer")
    private String answer;

    @Column(name = "media")
    private int media;

}
