package com.pat.soe.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Table(name = "fields") // this the table has not yet been created
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "field_numbers")
    private int field_numbers;

    @Column(name = "type_task")
    private int type_task;

    @Column(name = "bonus")
    private int bonus;

    @OneToOne(mappedBy = "field", cascade = CascadeType.ALL)
    private MiniGame miniGame;

    public Field(int field_numbers, int type_task, int bonus){
        this.field_numbers = field_numbers;
        this.type_task = type_task;
        this.bonus = bonus;
    }

}
