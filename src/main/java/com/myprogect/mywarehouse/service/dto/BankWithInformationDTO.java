package com.myprogect.mywarehouse.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class BankWithInformationDTO {
    private Long id;
    private Long bankCode;
    private String bankName;
    private List<PartnerAccountDTO> partnerAccounts = new ArrayList<>();
    private List<OrganizationAccountDTO> organizationAccounts = new ArrayList<>();
}
