package com.company.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "WAREHOUSE1")
@Table(name = "warehouse1")
public class Warehouse1 {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            name = "ID",
            nullable = false,
            updatable = false,
            unique = true
    )
    private Long id;

    @ManyToOne(
            targetEntity = Goods.class,
            cascade = { CascadeType.MERGE, CascadeType.REFRESH }
    )
    @JoinColumn(
            name = "GOOD_ID",
            foreignKey = @ForeignKey(
                    name = "fk_warehouse1_goods"
            )
    )
    private Goods goodId;

    @Column(
            name = "GOOD_COUNT"
    )
    private Long goodCount;

    public Warehouse1(Goods goodId, Long goodCount) {
        this.goodId = goodId;
        this.goodCount = goodCount;
    }
}
