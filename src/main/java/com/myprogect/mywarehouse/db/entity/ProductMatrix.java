package com.myprogect.mywarehouse.db.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;

@Entity()
@Table(name = "матрица_товара")
@Data
@Accessors(chain = true)
public class ProductMatrix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "наименование_товара")
    private String productName;

    @Column(name = "код_товара")
    private Integer productCode;
}
