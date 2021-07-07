package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.ConsignmentNote;
import com.myprogect.mywarehouse.db.entity.Storekeeper;
import com.myprogect.mywarehouse.db.repository.StorekeeperRepository;
import com.myprogect.mywarehouse.service.dto.ConsignmentNoteDTO;
import com.myprogect.mywarehouse.service.dto.StorekeeperDTO;
import com.myprogect.mywarehouse.service.dto.StorekeeperWithInformationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StorekeeperServiceImpl implements StorekeeperService {
    @Autowired
    StorekeeperRepository repository;

    @Override
    public StorekeeperWithInformationDTO byId(Long id) {
        Optional<Storekeeper> storekeeper = repository.findById(id);
        return storekeeper.map(value -> new StorekeeperWithInformationDTO()
                                .setId(value.getId())
                                .setEmployeeCode(value.getEmployeeCode())
                                .setName(value.getName())
                                .setSurname(value.getSurname())
                                .setMiddleName(value.getMiddleName())
                                .setLiabilityId(getLiability(value))
                                .setConsignmentNote(getConsignmentNote(value))).orElse(null);
    }

    @Override
    public StorekeeperDTO findByEmployeeCode(Integer employeeCode) {
        Optional<Storekeeper> storekeeperCode = repository.findByEmployeeCode(employeeCode);
        return storekeeperCode.map(value -> new StorekeeperDTO()
                .setEmployeeCode(value.getEmployeeCode()))
                .orElse(new StorekeeperDTO().setEmployeeCode(0));
    }

    @Override
    public List<StorekeeperDTO> findOnlyNameAndId() {
        List<StorekeeperDTO> storekeepers = new ArrayList<>();
        for(Storekeeper storekeeper : repository.findAll()){
            storekeepers.add(new StorekeeperDTO()
                    .setId(storekeeper.getId())
                    .setSurname(storekeeper.getSurname()));
        }
        return storekeepers;
    }

    @Override
    public void saveOrUpdate(StorekeeperDTO storekeeper) {
        Storekeeper storekeeperToDB = new Storekeeper();
        if(storekeeper.getId() == null) {
            storekeeperToDB.setLiability_id(storekeeper.getLiabilityId())
                    .setEmployeeCode(storekeeper.getEmployeeCode())
                    .setName(storekeeper.getName())
                    .setSurname(storekeeper.getSurname())
                    .setMiddleName(storekeeper.getMiddleName());
        }else {
            storekeeperToDB.setId(storekeeper.getId())
                    .setLiability_id(storekeeper.getLiabilityId())
                    .setEmployeeCode(storekeeper.getEmployeeCode())
                    .setName(storekeeper.getName())
                    .setSurname(storekeeper.getSurname())
                    .setMiddleName(storekeeper.getMiddleName());
        }
        repository.save(storekeeperToDB);
    }

    @Override
    public List<StorekeeperDTO> findAllRecord() {
        List<StorekeeperDTO> storekeeperDTOS = new ArrayList<>();
        for(Storekeeper storekeeper : repository.findAll()){
            storekeeperDTOS.add(new StorekeeperDTO()
                                .setId(storekeeper.getId())
                                .setEmployeeCode(storekeeper.getEmployeeCode())
                                .setName(storekeeper.getName())
                                .setSurname(storekeeper.getSurname())
                                .setMiddleName(storekeeper.getMiddleName())
                                .setLiabilityId(getLiability(storekeeper)));

        }
        return storekeeperDTOS;
    }

    private Integer getLiability(Storekeeper storekeeper){
        if(storekeeper.getEmployeeLiability().getLiabilityCode() == null){
            return null;
        }
        return storekeeper.getEmployeeLiability().getLiabilityCode();
    }

    private List<ConsignmentNoteDTO> getConsignmentNote(Storekeeper storekeeper){
        List<ConsignmentNoteDTO> consignmentNote = new ArrayList<>();
        if (storekeeper.getConsignmentNote() == null){
            consignmentNote.add(new ConsignmentNoteDTO());
            return consignmentNote;
        }
        for(ConsignmentNote note : storekeeper.getConsignmentNote()){
            consignmentNote.add(new ConsignmentNoteDTO()
                                .setId(note.getId())
                                .setConsignmentNoteId(String.valueOf(note.getConsignmentNoteId()))
                                .setConsignmentNoteDate(String.valueOf(note.getConsignmentNoteDate())));
        }
        return consignmentNote;
    }
}
