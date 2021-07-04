package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.service.dto.OperationsDTO;

import java.util.List;

public interface OperationsService {
    OperationsDTO byId(Long id);
    List<OperationsDTO> findAllRecord();
}
