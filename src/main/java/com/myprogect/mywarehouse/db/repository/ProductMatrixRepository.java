package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.ProductMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductMatrixRepository extends JpaRepository<ProductMatrix, Long> {
   Optional<ProductMatrix> findByProductCode(Integer productCode);
   Optional<ProductMatrix> findByProductName(String productName);
}
