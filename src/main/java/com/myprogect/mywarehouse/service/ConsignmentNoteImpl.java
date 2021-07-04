package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.*;
import com.myprogect.mywarehouse.db.repository.ConsignmentNoteRepository;
import com.myprogect.mywarehouse.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsignmentNoteImpl implements ConsignmentNoteService {
    @Autowired
    private ConsignmentNoteRepository repository;

    @Override
    public ConsignmentNoteDTO byId(Long id) {
        Optional<ConsignmentNote> consignmentNote = repository.findById(id);

        return consignmentNote.map(value -> new ConsignmentNoteDTO()
                            .setId(value.getId())
                            .setConsignmentNoteId(String.valueOf(value.getConsignmentNoteId()))
                            .setConsignmentNoteDate(String.valueOf(value.getConsignmentNoteDate()))
                            .setPartnerAccount(getPartnerAccountDTO(value))
                            .setOperation(getOperationDTO(value))
                            .setStorekeeperCode(getStorekeeperDTO(value))
                            .setWarehouses(getWarehouseDTO(value)))
                            .orElse(null);
    }

    @Override
    public ConsignmentNoteDTO findByConsignmentNoteId(Long consignmentNoteId) {
        Optional<ConsignmentNote> note = repository.findByConsignmentNoteId(consignmentNoteId);
        return note.map(value -> new ConsignmentNoteDTO()
                .setConsignmentNoteId(String.valueOf(value.getConsignmentNoteId())))
                .orElse(new ConsignmentNoteDTO().setConsignmentNoteId("0"));
    }

    @Override
    public ConsignmentNoteDTO filterByNoteId(String consignmentNoteId) {
        Optional<ConsignmentNote> note = repository
                .findByConsignmentNoteId(Long.valueOf(consignmentNoteId));

        return note.map(value -> new ConsignmentNoteDTO()
                .setId(value.getId())
                .setConsignmentNoteId(String.valueOf(value.getConsignmentNoteId()))
                .setConsignmentNoteDate(String.valueOf(value.getConsignmentNoteDate()))
                .setPartnerAccount(getPartnerAccountDTO(value))
                .setOperation(getOperationDTO(value))
                .setStorekeeperCode(getStorekeeperDTO(value))
                .setWarehouses(getWarehouseDTO(value)))
                .orElse(null);
    }

    @Override
    public void saveOrUpdate(ConsignmentNoteDTO consignmentNoteDTO) {
        ConsignmentNote consignmentNoteDB = new ConsignmentNote();

        consignmentNoteDB.setConsignmentNoteId(Long.valueOf(consignmentNoteDTO.getConsignmentNoteId()))
                            .setConsignmentNoteDate(Date.valueOf(consignmentNoteDTO.getConsignmentNoteDate()))
                            .setPartnerCode(consignmentNoteDTO.getPartnerCode())
                            .setTypeOfOperationCode(consignmentNoteDTO.getTypeOfOperationCode())
                            .setEmployeeCode(consignmentNoteDTO.getEmployeeCode());
        repository.save(consignmentNoteDB);
    }

    @Override
    public List<ConsignmentNoteDTO> findAllInformation() {
        List<ConsignmentNoteDTO> result = new ArrayList<>();
        for(ConsignmentNote consignment : repository.findAll()){
            result.add(new ConsignmentNoteDTO()
                    .setId(consignment.getId())
                    .setConsignmentNoteId(String.valueOf(consignment.getConsignmentNoteId()))
                    .setConsignmentNoteDate(String.valueOf(consignment.getConsignmentNoteDate()))
                    .setPartnerAccount(getPartnerAccountDTO(consignment))
                    .setOperation(getOperationDTO(consignment))
                    .setStorekeeperCode(getStorekeeperDTO(consignment)));
        }
        return result;
    }

    @Override
    public ConsignmentNoteDTO findByIdAndDate(String consignmentNoteId, String date) {
        Optional<ConsignmentNote> filterByIdAndDate = repository
                .findByConsignmentNoteIdAndAndConsignmentNoteDate(Long.valueOf(consignmentNoteId), Date.valueOf(date));
        return filterByIdAndDate.map(value -> new ConsignmentNoteDTO()
                .setId(value.getId())
                .setConsignmentNoteId(String.valueOf(value.getConsignmentNoteId()))
                .setConsignmentNoteDate(String.valueOf(value.getConsignmentNoteDate()))
                .setPartnerAccount(getPartnerAccountDTO(value))
                .setOperation(getOperationDTO(value))
                .setStorekeeperCode(getStorekeeperDTO(value))
                .setWarehouses(getWarehouseDTO(value)))
                .orElse(new ConsignmentNoteDTO());
    }

    @Override
    public List<ConsignmentNoteDTO> findByDate(String date) {
        List<ConsignmentNoteDTO> filterResults = new ArrayList<>();
        for(ConsignmentNote note : repository.findByConsignmentNoteDate(Date.valueOf(date))){
            filterResults.add(new ConsignmentNoteDTO()
                    .setId(note.getId())
                    .setConsignmentNoteId(String.valueOf(note.getConsignmentNoteId()))
                    .setConsignmentNoteDate(String.valueOf(note.getConsignmentNoteDate()))
                    .setPartnerAccount(getPartnerAccountDTO(note))
                    .setOperation(getOperationDTO(note))
                    .setStorekeeperCode(getStorekeeperDTO(note))
                    .setWarehouses(getWarehouseDTO(note)));
        }
        return filterResults;
    }

    @Override
    public void deleteData(Long id) {
        repository.deleteById(id);
    }

    private PartnerAccountDTO getPartnerAccountDTO(ConsignmentNote consignment){
        PartnerAccount partnerAccount = consignment.getPartnerAccount();

        return new PartnerAccountDTO()
                .setId(partnerAccount.getId())
                .setPartnerCode(partnerAccount.getPartnerCode())
                .setOrganizationName(partnerAccount.getOrganizationName())
                .setCodeOfPayer(partnerAccount.getCodeOfPayer())
                .setSettlementAccount(partnerAccount.getSettlementAccount());

    }

    private OperationsDTO getOperationDTO(ConsignmentNote consignment){
        Operations operations = consignment.getOperation();
        return new OperationsDTO()
                .setId(operations.getId())
                .setTypeOfOperation(operations.getTypeOfOperation());
    }

    private StorekeeperDTO getStorekeeperDTO(ConsignmentNote consignment){
        Storekeeper storekeeper = consignment.getStorekeeper();
        return new StorekeeperDTO()
                .setId(storekeeper.getId())
                .setEmployeeCode(storekeeper.getEmployeeCode())
                .setName(storekeeper.getName())
                .setMiddleName(storekeeper.getMiddleName())
                .setSurname(storekeeper.getSurname());
    }

    private List<WarehouseDTO> getWarehouseDTO(ConsignmentNote consignment){
        List<WarehouseDTO> warehouseDTOList = new ArrayList<>();

        for(Warehouse warehouse : consignment.getWarehouses()){
            warehouseDTOList.add(new WarehouseDTO().setProductMatrix(getProduct(warehouse))
                            .setPrice(String.valueOf(warehouse.getPrice()))
                            .setQuantity(String.valueOf(warehouse.getQuantity())));
        }
        return warehouseDTOList;
    }

    private ProductMatrixDTO getProduct(Warehouse warehouse){
        ProductMatrix productMatrix = warehouse.getProductMatrix();
        return new ProductMatrixDTO().setProductCode(productMatrix.getProductCode())
                                    .setProductName(productMatrix.getProductName());
    }
}
