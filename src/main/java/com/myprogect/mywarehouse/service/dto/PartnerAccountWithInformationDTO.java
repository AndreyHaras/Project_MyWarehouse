package com.myprogect.mywarehouse.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class PartnerAccountWithInformationDTO {
    private Long id;
    private Long partnerCode;
    private String organizationName;
    private Long codeOfPayer;
    private Long settlementAccount;
    private List<ConsignmentNoteDTO> consignmentNotes = new ArrayList<>();
    private BankDTO partnerBank;
}
