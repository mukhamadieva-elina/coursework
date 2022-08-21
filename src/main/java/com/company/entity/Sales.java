package com.company.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "SALES")
@Table(name = "sales")
public class Sales {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "sales_sequence"
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
                    name = "fk_sales_goods"
            )
    )
    private Goods goodId;
    @Column(
            name = "GOOD_COUNT"
    )
    private Long goodCount;
    @Column(
            name = "CREATE_DATE",
            nullable = false
    )
    private LocalDateTime createDate;

    public Sales(Goods goodId, Long goodCount, LocalDateTime createDate) {
        this.goodId = goodId;
        this.goodCount = goodCount;
        this.createDate = createDate;
    }
}
