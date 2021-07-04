package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.service.dto.BankDTO;
import com.myprogect.mywarehouse.service.dto.BankWithInformationDTO;
import java.util.List;

public interface BankService {
    BankDTO byId(Long id);
    void saveData(BankDTO bankDTO);
    List<BankDTO> findAllRecord();
    BankWithInformationDTO findAllInformationById(Long id);
    BankDTO findByName(String nameOfBank);
    BankDTO findByCode(Long bankCode);
}
