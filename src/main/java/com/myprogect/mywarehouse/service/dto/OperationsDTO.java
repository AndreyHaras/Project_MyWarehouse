package com.myprogect.mywarehouse.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OperationsDTO {
    private Long id;
    private String typeOfOperation;
}
