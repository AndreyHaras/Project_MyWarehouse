package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.PartnerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerAccountRepository extends JpaRepository<PartnerAccount, Long> {
    Optional<PartnerAccount> findByPartnerCode(Long partnerCode);
    Optional<PartnerAccount> findByOrganizationName(String organizationName);
    Optional<PartnerAccount> findByCodeOfPayer(Long codeOfPayer);
    Optional<PartnerAccount> findBySettlementAccount(Long settlementAccount);
}
