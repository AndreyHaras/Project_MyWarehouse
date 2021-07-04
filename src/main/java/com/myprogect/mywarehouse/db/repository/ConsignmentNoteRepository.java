package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.ConsignmentNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ConsignmentNoteRepository extends JpaRepository<ConsignmentNote, Long>{
    List<ConsignmentNote> findByConsignmentNoteDate(Date date);
    Optional<ConsignmentNote> findByConsignmentNoteId(Long consignmentNoteId);
    Optional<ConsignmentNote> findByConsignmentNoteIdAndAndConsignmentNoteDate(Long consignmentNoteId, Date date);
}
