package com.myprogect.mywarehouse.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductMatrixDTO {
    private Long id;
    private String productName;
    private Integer productCode;
}
