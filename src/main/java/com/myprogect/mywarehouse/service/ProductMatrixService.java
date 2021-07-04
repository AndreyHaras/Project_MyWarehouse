package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.service.dto.ProductMatrixDTO;
import java.util.List;

public interface ProductMatrixService {
    ProductMatrixDTO byId(Long id);
    void saveProduct(ProductMatrixDTO product);
    ProductMatrixDTO findByProductCode(Integer productCode);
    ProductMatrixDTO findByProductName(String productName);
    List<ProductMatrixDTO> findAllRecord();

}
