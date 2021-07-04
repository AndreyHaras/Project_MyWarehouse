package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.Liability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiabilityRepository extends JpaRepository<Liability, Long> {
}
