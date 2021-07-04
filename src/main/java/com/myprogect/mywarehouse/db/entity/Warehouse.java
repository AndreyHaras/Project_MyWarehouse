package com.myprogect.mywarehouse.db.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import javax.persistence.*;

@Entity()
@Table(name = "склад")
@Data
@Accessors(chain = true)
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "накладная_id")
    @Getter(AccessLevel.NONE)
    private Long consignmentId;

    @Column(name = "товар_id")
    private Long product_id;

    @Column(name = "цена")
    private Double price;

    @Column(name = "количество")
    private Double quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "накладная_id", insertable = false, updatable = false)
    private ConsignmentNote consignmentCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "товар_id", insertable = false, updatable = false)
    private ProductMatrix productMatrix;
}
