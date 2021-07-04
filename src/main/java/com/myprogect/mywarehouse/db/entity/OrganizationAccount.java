package com.myprogect.mywarehouse.db.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import javax.persistence.*;

@Entity()
@Table(name = "счет_организации")
@Data
@EqualsAndHashCode(exclude = {"organizationBank"})
@Accessors(chain = true)
public class OrganizationAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "наименование")
    private String organizationName;

    @Column(name = "код_плательщика")
    private Long codeOfPayer;

    @Column(name = "расчетный_счет")
    private Long settlementAccount;

    @Column(name = "id_банка")
    @Getter(AccessLevel.NONE)
    private Long bank_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_банка", insertable = false, updatable = false)
    private Bank organizationBank;

}
