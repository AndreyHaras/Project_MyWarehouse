package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.ConsignmentNote;
import com.myprogect.mywarehouse.db.entity.ProductMatrix;
import com.myprogect.mywarehouse.db.entity.Warehouse;
import com.myprogect.mywarehouse.db.repository.WarehouseRepository;
import com.myprogect.mywarehouse.service.dto.ConsignmentNoteDTO;
import com.myprogect.mywarehouse.service.dto.ProductMatrixDTO;
import com.myprogect.mywarehouse.service.dto.WarehouseDTO;
import com.myprogect.mywarehouse.service.search.WarehouseSumGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ProductMatrixService productMatrixService;

    @Override
    public WarehouseDTO byId(Long id) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        return warehouse.map(value -> new WarehouseDTO()
                            .setId(value.getId())
                            .setPrice(String.valueOf(value.getPrice()))
                            .setQuantity(String.valueOf(value.getQuantity()))
                            .setConsignmentCode(getConsignmentNotes(value))
                            .setProductMatrix(getProductMatrix(value))).orElse(null);
    }

    @Override
    public List<WarehouseDTO> byConsignmentNoteId(Long id) {
        List<WarehouseDTO> warehouseDTOList = new ArrayList<>();
        for(Warehouse warehouse : warehouseRepository.findByConsignmentId(id)){
            warehouseDTOList.add(new WarehouseDTO()
                    .setId(warehouse.getId())
                    .setPrice(String.valueOf(warehouse.getPrice()))
                    .setQuantity(String.valueOf(warehouse.getQuantity()))
                    .setConsignmentCode(getConsignmentNotes(warehouse))
                    .setProductMatrix(getProductMatrix(warehouse)));
        }
        return warehouseDTOList;
    }

    @Override
    public void saveData(WarehouseDTO warehouseDTO) {
        Warehouse warehouseDB = new Warehouse();
        warehouseDB.setId(warehouseDTO.getId())
                    .setConsignmentId(warehouseDTO.getConsignmentId())
                    .setProduct_id(warehouseDTO.getProductId())
                    .setPrice(Double.valueOf(warehouseDTO.getPrice()))
                    .setQuantity(Double.valueOf(warehouseDTO.getQuantity()));
        warehouseRepository.save(warehouseDB);
    }

    @Override
    public List<WarehouseDTO> getAllRecord() {
        List<WarehouseDTO> warehouseDTOList = new ArrayList<>();
        for(Warehouse warehouse : warehouseRepository.findAll()){
            warehouseDTOList.add(new WarehouseDTO()
                    .setId(warehouse.getId())
                    .setPrice(String.valueOf(warehouse.getPrice()))
                    .setQuantity(String.valueOf(warehouse.getQuantity()))
                    .setConsignmentCode(getConsignmentNotes(warehouse))
                    .setProductMatrix(getProductMatrix(warehouse)));
        }
        return warehouseDTOList;
    }

    @Override
    public List<WarehouseDTO> findTotalSumQuantity() {
        List<WarehouseDTO> warehouseDTOList = new ArrayList<>();
        for (WarehouseSumGroup warehouse : warehouseRepository.findAllAndGroup()){
            if(warehouse == null){
                warehouseDTOList.add(new WarehouseDTO());
                return warehouseDTOList;
            }
            ProductMatrixDTO product = productMatrixService.byId(warehouse.getProductId());
            warehouseDTOList.add(new WarehouseDTO()
                    .setQuantity(String.valueOf(warehouse.getQuantity()))
                    .setPrice(String.valueOf(warehouse.getPrice()))
                    .setProductMatrix(product));
        }
        return warehouseDTOList;
    }

    private ConsignmentNoteDTO getConsignmentNotes(Warehouse warehouse){
        ConsignmentNote consignmentNote = warehouse.getConsignmentCode();
        return new ConsignmentNoteDTO()
                .setId(consignmentNote.getId())
                .setConsignmentNoteId(String.valueOf(consignmentNote.getConsignmentNoteId()))
                .setConsignmentNoteDate(String.valueOf(consignmentNote.getConsignmentNoteDate()));
    }

    private ProductMatrixDTO getProductMatrix(Warehouse warehouse){
        ProductMatrix product = warehouse.getProductMatrix();
        return new ProductMatrixDTO()
                .setId(product.getId())
                .setProductCode(product.getProductCode())
                .setProductName(product.getProductName());
    }
}
