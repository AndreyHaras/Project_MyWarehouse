package com.myprogect.mywarehouse.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BankDTO {
    private Long id;
    private Long bankCode;
    private String bankName;
}
