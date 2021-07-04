package com.myprogect.mywarehouse.db.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.util.List;

@Entity()
@Table(name = "кладовщик")
@Data
@EqualsAndHashCode(exclude = {"employeeLiability","consignmentNote"})
@Accessors(chain = true)
public class Storekeeper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "фамилия")
    private String surname;

    @Column(name = "имя")
    private String name;

    @Column(name = "отчество")
    private String middleName;

    @Column(name = "код_сотрудника")
    private Integer employeeCode;

    @Column(name = "id_ответственности")
    @Getter(AccessLevel.NONE)
    private Integer liability_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ответственности", insertable = false, updatable = false)
    private Liability employeeLiability;

    @OneToMany(mappedBy = "storekeeper",
            fetch = FetchType.LAZY)
    private List<ConsignmentNote> consignmentNote;
}
