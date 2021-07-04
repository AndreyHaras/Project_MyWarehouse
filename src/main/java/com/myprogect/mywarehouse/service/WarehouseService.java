package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.service.dto.WarehouseDTO;
import java.util.List;

public interface WarehouseService {
    WarehouseDTO byId(Long id);
    List<WarehouseDTO> byConsignmentNoteId(Long id);
    void saveData(WarehouseDTO warehouseDTO);
    List<WarehouseDTO> getAllRecord();
    List<WarehouseDTO> findTotalSumQuantity();
}
