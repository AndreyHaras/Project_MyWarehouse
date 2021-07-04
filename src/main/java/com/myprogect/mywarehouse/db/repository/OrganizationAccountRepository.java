package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.OrganizationAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrganizationAccountRepository extends JpaRepository<OrganizationAccount, Long> {
    Optional<OrganizationAccount> findByOrganizationName(String organizationName);
    Optional<OrganizationAccount> findByCodeOfPayer(Long codeOfPayer);
    Optional<OrganizationAccount> findBySettlementAccount(Long settlementAccount);
}
