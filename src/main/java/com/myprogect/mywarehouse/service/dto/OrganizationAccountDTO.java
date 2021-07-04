package com.myprogect.mywarehouse.service.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrganizationAccountDTO {
    private Long id;
    private String organizationName;
    private Long codeOfPayer;
    private Long settlementAccount;
    private Long bankCode;
}
