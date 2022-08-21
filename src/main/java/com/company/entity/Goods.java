package com.company.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "GOODS")
@Table(name = "goods", uniqueConstraints = {
            @UniqueConstraint(
                name = "name_unique",
                columnNames = "NAME"
            )
})
public class Goods {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "goods_sequence"
    )
    @Column(
            name = "ID",
            nullable = false,
            updatable = false,
            unique = true
    )
    private Long id;

    @Column(
            name = "NAME",
            nullable = false

    )
    private String name;

    @Column(
            name = "PRIORITY",
            nullable = false
    )
    private Double priority;

    public Goods(String name,
                 Double priority) {
        this.name = name;
        this.priority = priority;
    }
}
