package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.service.dto.OrganizationAccountDTO;
import java.util.List;

public interface OrganizationAccountService {
    OrganizationAccountDTO byId(Long id);
    List<OrganizationAccountDTO> findAllRecords();
    void saveDate(OrganizationAccountDTO organizationAccount);
    OrganizationAccountDTO findByOrganizationName(String organizationName);
    OrganizationAccountDTO findByCodeOfPayer(Long codeOfPayer);
    OrganizationAccountDTO findBySettlementAccount(Long settlementAccount);
}
