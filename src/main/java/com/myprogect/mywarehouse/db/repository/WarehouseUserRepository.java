package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.WarehouseUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WarehouseUserRepository extends JpaRepository<WarehouseUsers, Long> {
    Optional<WarehouseUsers> findByUserName(String userName);
    Optional<WarehouseUsers> findByUserPassword(String userPassword);
}
