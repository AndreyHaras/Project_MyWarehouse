package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.ProductMatrix;
import com.myprogect.mywarehouse.db.repository.ProductMatrixRepository;
import com.myprogect.mywarehouse.service.dto.ProductMatrixDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductMatrixServiceImpl implements ProductMatrixService{
    @Autowired
    ProductMatrixRepository repository;

    @Override
    public ProductMatrixDTO byId(Long id) {
        Optional<ProductMatrix> product = repository.findById(id);
        return product.map(value -> new ProductMatrixDTO()
                        .setId(value.getId())
                        .setProductName(value.getProductName())
                        .setProductCode(value.getProductCode())).orElse(null);
    }

    @Override
    public ProductMatrixDTO findByProductCode(Integer productCode) {
        Optional<ProductMatrix> productByCode = repository.findByProductCode(productCode);
        return productByCode.map(value -> new ProductMatrixDTO()
                .setProductCode(value.getProductCode()))
                .orElse(new ProductMatrixDTO().setProductCode(0));
    }

    @Override
    public ProductMatrixDTO findByProductName(String productName) {
        Optional<ProductMatrix> productByName = repository.findByProductName(productName);
        return productByName.map(value -> new ProductMatrixDTO()
                .setProductName(value.getProductName()))
                .orElse(new ProductMatrixDTO().setProductName(""));
    }

    @Override
    public void saveProduct(ProductMatrixDTO product) {
        ProductMatrix addProduct = new ProductMatrix();
        addProduct.setProductCode(product.getProductCode())
                    .setProductName(product.getProductName());
        repository.save(addProduct);
    }

    @Override
    public List<ProductMatrixDTO> findAllRecord() {
        List<ProductMatrixDTO> productMatrix = new ArrayList<>();
        for(ProductMatrix product : repository.findAll()){
            productMatrix.add(new ProductMatrixDTO()
                            .setId(product.getId())
                            .setProductName(product.getProductName())
                            .setProductCode(product.getProductCode()));
        }
        return productMatrix;
    }

}
