package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.Operations;
import com.myprogect.mywarehouse.db.repository.OperationsRepository;
import com.myprogect.mywarehouse.service.dto.OperationsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationsService {
    @Autowired
    OperationsRepository repository;

    @Override
    public OperationsDTO byId(Long id) {
        Optional<Operations> operations = repository.findById(id);

        return operations.map(value -> new OperationsDTO()
                .setId(value.getId())
                .setTypeOfOperation(value.getTypeOfOperation()))
                .orElse(new OperationsDTO().setId(0L).setTypeOfOperation(""));
    }

    @Override
    public List<OperationsDTO> findAllRecord() {
        List<OperationsDTO> operations = new ArrayList<>();
        for(Operations operation : repository.findAll()){
            operations.add(new OperationsDTO()
                        .setId(operation.getId())
                        .setTypeOfOperation(operation.getTypeOfOperation()));
        }
        return operations;
    }
}
