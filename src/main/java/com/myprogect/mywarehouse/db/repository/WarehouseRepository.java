package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.Warehouse;
import com.myprogect.mywarehouse.service.search.WarehouseSumGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    List<Warehouse> findByConsignmentId(Long consignmentId);
    @Query(value = "SELECT w.price AS price, sum(w.quantity) AS quantity, w.product_id AS productId FROM Warehouse w GROUP BY price, productId")
    List<WarehouseSumGroup> findAllAndGroup();
}
