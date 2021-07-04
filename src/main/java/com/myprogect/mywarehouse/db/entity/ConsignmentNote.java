package com.myprogect.mywarehouse.db.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity()
@Table(name = "накладная")
@Data
@EqualsAndHashCode(exclude = {"storekeeper", "partnerAccount","operation","warehouses"})
@Accessors(chain = true)
public class ConsignmentNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "номер_накладной")
    private Long consignmentNoteId;

    @Column(name = "дата_накладной")
    private Date consignmentNoteDate;

    @Column(name = "контрагент_id")
    @Getter(AccessLevel.NONE)
    private Long partnerCode;

    @Column(name = "операция_id")
    @Getter(AccessLevel.NONE)
    private Long typeOfOperationCode;


    @Column(name = "сотрудник_id")
    @Getter(AccessLevel.NONE)
    private Integer employeeCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "сотрудник_id", insertable = false, updatable = false)
    private Storekeeper storekeeper;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "контрагент_id", insertable = false, updatable = false)
    private PartnerAccount partnerAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "операция_id", insertable = false, updatable = false)
    private Operations operation;

    @OneToMany(mappedBy = "consignmentCode", cascade = CascadeType.ALL, orphanRemoval = false,
            fetch = FetchType.LAZY)
    private List<Warehouse> warehouses;
}
