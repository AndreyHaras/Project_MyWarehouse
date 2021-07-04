package com.myprogect.mywarehouse.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@Accessors(chain = true)
public class StorekeeperWithInformationDTO {
    private Long id;
    private Integer employeeCode;
    private String surname;
    private String name;
    private String middleName;
    private Integer liabilityId;
    private List<ConsignmentNoteDTO> consignmentNote;
}
