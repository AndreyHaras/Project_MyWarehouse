package com.myprogect.mywarehouse.service.dto;

import com.myprogect.mywarehouse.db.entity.Liability;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(exclude = "liabilityId")
@Accessors(chain = true)
public class StorekeeperDTO {
    private Long id;

    private Integer employeeCode;

    private String surname;

    private String name;

    private String middleName;

    private Integer liabilityId;
}
