package com.myprogect.mywarehouse.db.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "контрагент")
@Data
@EqualsAndHashCode(exclude = {"consignmentNotes","partnerBank"})
@Accessors(chain = true)
public class PartnerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "код_контрагента")
    private Long partnerCode;

    @Column(name = "наименование_организации")
    private String organizationName;

    @Column(name = "код_плательщика")
    private Long codeOfPayer;

    @Column(name = "расчетный_счет")
    private Long settlementAccount;

    @Column(name = "id_банка")
    @Getter(AccessLevel.NONE)
    private Long bank_id;

    @OneToMany(mappedBy = "partnerAccount",
            fetch = FetchType.LAZY)
    private List<ConsignmentNote> consignmentNotes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_банка", insertable = false, updatable = false)
    private Bank partnerBank;

}
