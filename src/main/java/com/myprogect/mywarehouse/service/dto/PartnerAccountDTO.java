package com.myprogect.mywarehouse.service.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PartnerAccountDTO {
    private Long id;
    private Long partnerCode;
    private String organizationName;
    private Long codeOfPayer;
    private Long settlementAccount;
    private Long bankCode;
}
