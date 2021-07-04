package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.service.dto.LiabilityDTO;

import java.util.List;

public interface LiabilityService {
    LiabilityDTO byId(Long id);
    List<LiabilityDTO> findAllRecord();
}
