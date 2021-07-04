package com.myprogect.mywarehouse.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@Accessors(chain = true)
public class ConsignmentNoteDTO {
    private Long id;

    private String consignmentNoteId;

    private String consignmentNoteDate;

    private Long partnerCode;

    private Long typeOfOperationCode;

    private Integer employeeCode;

    private PartnerAccountDTO partnerAccount;

    private OperationsDTO operation;

    private StorekeeperDTO storekeeperCode;

    private List<WarehouseDTO> warehouses;
}
