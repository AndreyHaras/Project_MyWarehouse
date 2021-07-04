package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.Liability;
import com.myprogect.mywarehouse.db.repository.LiabilityRepository;
import com.myprogect.mywarehouse.service.dto.LiabilityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LiabilityServiceImpl implements LiabilityService {
    @Autowired
    LiabilityRepository repository;

    @Override
    public LiabilityDTO byId(Long id) {
        Optional<Liability> liabilityDTO = repository.findById(id);

        return liabilityDTO.map(value -> new LiabilityDTO()
                                    .setId(value.getId())
                                    .setLiabilityCode(value.getLiabilityCode())).orElse(null);
    }

    @Override
    public List<LiabilityDTO> findAllRecord() {
        List<LiabilityDTO> liability = new ArrayList<>();

        for(Liability liabilityFromDB : repository.findAll()){
            liability.add(new LiabilityDTO().setId(liabilityFromDB.getId())
                                            .setLiabilityCode(liabilityFromDB.getLiabilityCode()));
        }
        return liability;
    }
}
