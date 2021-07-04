package com.myprogect.mywarehouse.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WarehouseDTO {
    private Long id;

    private Long consignmentId;

    private Long productId;

    private String price;

    private String quantity;

    private ConsignmentNoteDTO consignmentCode;

    private ProductMatrixDTO productMatrix;
}
