package com.myprogect.mywarehouse.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.util.List;

@Entity()
@Table(name = "мат_ответственность")
@Data
@EqualsAndHashCode(exclude = {"employeeLiability"})
@Accessors(chain = true)
public class Liability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "код_мо")
    private Integer liabilityCode;

    @OneToMany(mappedBy = "employeeLiability",
            fetch = FetchType.LAZY)
    private List<Storekeeper> employeeLiability;
}
