package com.myprogect.mywarehouse.service.search;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConsignmentSearch {
    private Long id;

    private Long consignmentNoteId;

    private String consignmentNoteDate;
}
