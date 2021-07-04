package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.service.dto.PartnerAccountDTO;
import com.myprogect.mywarehouse.service.dto.PartnerAccountWithInformationDTO;
import java.util.List;

public interface PartnerAccountService {
    PartnerAccountWithInformationDTO byId(Long id);
    List<PartnerAccountDTO> findAllRecord();
    PartnerAccountDTO saveData(PartnerAccountDTO partnerAccountDTO);
    List<PartnerAccountDTO> findOnlyNameAndId();
    PartnerAccountDTO findByPartnerCode(Long partnerCode);
    PartnerAccountDTO findByOrganizationName(String organizationName);
    PartnerAccountDTO findByCodeOfPayer(Long codeOfPayer);
    PartnerAccountDTO findBySettlementAccount(Long settlementAccount);
}
