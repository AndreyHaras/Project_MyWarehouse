package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.Storekeeper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorekeeperRepository extends JpaRepository<Storekeeper, Long> {
    Optional<Storekeeper> findByEmployeeCode(Integer employeeCode);
}
