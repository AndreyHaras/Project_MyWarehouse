package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.service.dto.StorekeeperDTO;
import com.myprogect.mywarehouse.service.dto.StorekeeperWithInformationDTO;
import java.util.List;

public interface StorekeeperService {
    StorekeeperWithInformationDTO byId(Long id);
    void saveOrUpdate(StorekeeperDTO storekeeper);
    List<StorekeeperDTO> findAllRecord();
    List<StorekeeperDTO> findOnlyNameAndId();
    StorekeeperDTO findByEmployeeCode(Integer employeeCode);
}
