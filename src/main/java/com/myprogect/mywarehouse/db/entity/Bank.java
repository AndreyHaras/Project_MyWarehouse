package com.myprogect.mywarehouse.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "банк")
@Data
@Accessors(chain = true)
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "код_банка")
    private Long bankCode;

    @Column(name = "наименование_банка")
    private String bankName;

    @OneToMany(mappedBy = "partnerBank", fetch = FetchType.LAZY)
    private List<PartnerAccount> partnerAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "organizationBank", fetch = FetchType.LAZY)
    private List<OrganizationAccount> organizationAccounts = new ArrayList<>();
}
