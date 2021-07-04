package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.service.dto.ConsignmentNoteDTO;
import java.util.List;

public interface ConsignmentNoteService {
    ConsignmentNoteDTO byId(Long id);
    ConsignmentNoteDTO findByConsignmentNoteId(Long consignmentNoteId);
    ConsignmentNoteDTO findByIdAndDate(String date, String consignmentNoteId);
    ConsignmentNoteDTO filterByNoteId(String consignmentNoteId);
    List<ConsignmentNoteDTO> findByDate(String date);
    void saveOrUpdate(ConsignmentNoteDTO consignmentNoteDTO);
    void deleteData(Long id);
    List<ConsignmentNoteDTO> findAllInformation();
}
