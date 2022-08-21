package com.company.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "WAREHOUSE2")
@Table(name = "warehouse2")
public class Warehouse2 {

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
                    name = "fk_warehouse2_goods"
            )
    )
    private Goods goodId;

    @Column(
            name = "GOOD_COUNT"
    )
    private Long goodCount;

    public Warehouse2(Goods goodId, Long goodCount) {
        this.goodId = goodId;
        this.goodCount = goodCount;
    }
}
