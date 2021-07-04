package com.myprogect.mywarehouse.service.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WarehouseUserDTO {
    private Long id;
    private String userName;
    private String userPassword;
    private String userRole;
    private Boolean access;
}
