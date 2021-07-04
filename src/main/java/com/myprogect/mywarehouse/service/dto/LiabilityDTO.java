package com.myprogect.mywarehouse.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@Accessors(chain = true)
public class LiabilityDTO {
    private Long id;
    private Integer liabilityCode;
    private List<StorekeeperDTO> employeeLiability;
}
