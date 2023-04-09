package com.pat.soe.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Table(name = "Field") // this the table has not yet been created
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "field_numbers")
    private int field_numbers;

    @Column(name = "type_task")
    private int type_task;

    @Column(name = "bonus")
    private int bonus;

}
