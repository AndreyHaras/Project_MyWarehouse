package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.Operations;
import com.myprogect.mywarehouse.db.entity.OrganizationAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationsRepository extends JpaRepository<Operations, Long> {
}
