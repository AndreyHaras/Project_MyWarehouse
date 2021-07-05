package com.myprogect.mywarehouse.web.controller;

import com.myprogect.mywarehouse.service.*;
import com.myprogect.mywarehouse.service.dto.ConsignmentNoteDTO;
import com.myprogect.mywarehouse.service.dto.ProductMatrixDTO;
import com.myprogect.mywarehouse.service.dto.WarehouseDTO;
import com.myprogect.mywarehouse.service.validator.ConsignmentNoteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;
import static com.myprogect.mywarehouse.web.constant.Constants.BaseController.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(CONSIGNMENT)
public class ConsignmentNoteController {

    @Autowired
    ConsignmentNoteService consignmentNoteService;
    @Autowired
    PartnerAccountService partnerAccountService;
    @Autowired
    StorekeeperService storekeeperService;
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    OperationsService operationsService;
    @Autowired
    ProductMatrixService productMatrixService;
    @Autowired
    ConsignmentNoteValidator noteValidator;

    @RequestMapping(value = LISTING)
    public String showAllRecords(Model model){
        ConsignmentNoteDTO filter = new ConsignmentNoteDTO();
        List<ConsignmentNoteDTO> consignmentList = consignmentNoteService.findAllInformation();
        model.addAttribute("consignmentList",consignmentList);
        model.addAttribute("consignmentNoteFilter", filter);
        return "main_page";
    }

    @RequestMapping(value = FILTER, method = RequestMethod.POST)
    public String filterRecords(@ModelAttribute("consignmentNoteFilter") ConsignmentNoteDTO filter, Model model){
        if(filter.getConsignmentNoteDate().compareTo("") == 0 &&
                filter.getConsignmentNoteId().compareTo("") == 0){
            return "redirect:" + CONSIGNMENT + LISTING;
        }else if(filter.getConsignmentNoteDate().compareTo("") != 0 &&
                filter.getConsignmentNoteId().compareTo("") != 0){
            ConsignmentNoteDTO filterResult = consignmentNoteService
                    .findByIdAndDate(filter.getConsignmentNoteId(), filter.getConsignmentNoteDate());
            model.addAttribute("consignmentList", filterResult);
            return "main_page";
        }else if(filter.getConsignmentNoteDate().compareTo("") != 0){
            List<ConsignmentNoteDTO> filterDate = consignmentNoteService.findByDate(filter.getConsignmentNoteDate());
            model.addAttribute("consignmentList",filterDate);
            return "main_page";
        }else if(filter.getConsignmentNoteId().compareTo("") != 0){
            ConsignmentNoteDTO filterNoteId = consignmentNoteService.filterByNoteId(filter.getConsignmentNoteId());
            model.addAttribute("consignmentList", filterNoteId);
            return "main_page";
        }

        return "redirect:" + CONSIGNMENT + LISTING;
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = DELETE + "{id}")
    public String recordDelete(@PathVariable("id") Long id){
        if(id == null) throw new ResponseStatusException(NOT_FOUND, "Unable to find id");

        consignmentNoteService.deleteData(id);
        return "redirect:" + CONSIGNMENT + LISTING;
    }

    @RequestMapping(value = SHOWINFORMATION + "{id}")
    public String showInformationOfRecord(@PathVariable("id") Long id, Model model){
        ConsignmentNoteDTO consignmentNoteDTO = consignmentNoteService.byId(id);

        if(consignmentNoteDTO == null) throw new ResponseStatusException(NOT_FOUND, "Unable to find id");

        model.addAttribute("consignmentInfo", consignmentNoteDTO);
        return "consignment_info";
    }

    @RequestMapping(value = ADD, method = RequestMethod.GET)
    public String addConsignmentNote(Model model){
        ConsignmentNoteDTO consignmentNoteDTO = new ConsignmentNoteDTO();
        model.addAttribute("consignment_note",consignmentNoteDTO);
        model.addAttribute("partner_info", partnerAccountService.findOnlyNameAndId());
        model.addAttribute("employee_info", storekeeperService.findOnlyNameAndId());
        model.addAttribute("operation_info", operationsService.findAllRecord());
        return "consignment_add";
    }

    @RequestMapping(value = ADD, method = RequestMethod.POST)
    public String addNewConsignmentNote(@Valid @ModelAttribute("consignment_note")
                              ConsignmentNoteDTO consignmentNoteDTO,
                                BindingResult bindingResult, Model model){
        ConsignmentNoteDTO consignmentNoteToDB = addNewNote(consignmentNoteDTO);

        noteValidator.validate(consignmentNoteToDB,bindingResult);

        if(bindingResult.hasErrors()){

            model.addAttribute("partner_info", partnerAccountService.findOnlyNameAndId());
            model.addAttribute("employee_info", storekeeperService.findOnlyNameAndId());
            model.addAttribute("operation_info", operationsService.findAllRecord());
            return "consignment_add";
        }

        consignmentNoteService.saveOrUpdate(consignmentNoteToDB);
        return "redirect:" + CONSIGNMENT + LISTING;
    }

    @RequestMapping(value = ADDPRODUCT + "{id}", method = RequestMethod.GET)
    public String addProductToConsignmentNote(@PathVariable("id") Long id, Model model){
        ConsignmentNoteDTO consignmentNoteDTO = consignmentNoteService.byId(id);
        if(consignmentNoteDTO == null) throw new ResponseStatusException(NOT_FOUND, "Unable to find id");

        List<ProductMatrixDTO> productMatrix = productMatrixService.findAllRecord();
        WarehouseDTO warehouseDTO = new WarehouseDTO();
        model.addAttribute("consignment_note", consignmentNoteDTO);
        model.addAttribute("products", productMatrix);
        model.addAttribute("warehouse", warehouseDTO);
        return "consignment_product_add";
    }


    @RequestMapping(value = ADDPRODUCT, method = RequestMethod.POST)
    public String addMoreProductToConsignmentNote(@Valid @ModelAttribute("consignment_note")
                                                              ConsignmentNoteDTO consignmentNoteDTO,
                                                  WarehouseDTO warehouseDTO){
        WarehouseDTO warehouseToDB = new WarehouseDTO();

        warehouseToDB.setConsignmentId(consignmentNoteDTO.getId())
                        .setProductId(warehouseDTO.getProductId())
                        .setPrice(warehouseDTO.getPrice())
                        .setQuantity(warehouseDTO.getQuantity());
        warehouseService.saveData(warehouseToDB);

        return "redirect:" + CONSIGNMENT + ADDPRODUCT + consignmentNoteDTO.getId();
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = UPDATENOTE + "{id}", method = RequestMethod.GET)
    public String updateConsignmentNote(@PathVariable("id") Long id, Model model){
        ConsignmentNoteDTO consignmentNoteDTO = consignmentNoteService.byId(id);
        if(consignmentNoteDTO == null) throw new ResponseStatusException(NOT_FOUND, "Unable to find id");

        model.addAttribute("consignment_note", consignmentNoteDTO);
        model.addAttribute("partner_info", partnerAccountService.findOnlyNameAndId());
        model.addAttribute("employee_info", storekeeperService.findOnlyNameAndId());
        model.addAttribute("operation_info", operationsService.findAllRecord());
        return "consignment_update";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = UPDATENOTE, method = RequestMethod.POST)
    public String updateOldConsignmentNote(@Valid @ModelAttribute("consignment_note")
                                                       ConsignmentNoteDTO consignmentNoteDTO,
                                           BindingResult bindingResult, Model model){

        ConsignmentNoteDTO consignmentNoteUpdateToDB = addNewNote(consignmentNoteDTO);

        noteValidator.validate(consignmentNoteUpdateToDB,bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("partner_info", partnerAccountService.findOnlyNameAndId());
            model.addAttribute("employee_info", storekeeperService.findOnlyNameAndId());
            model.addAttribute("operation_info", operationsService.findAllRecord());
            return "consignment_update";
        }

        consignmentNoteService.saveOrUpdate(consignmentNoteUpdateToDB);
        return "redirect:" + CONSIGNMENT + LISTING;
    }

    private ConsignmentNoteDTO addNewNote(ConsignmentNoteDTO data){
        ConsignmentNoteDTO consignmentNote = new ConsignmentNoteDTO();

        consignmentNote.setConsignmentNoteId(data.getConsignmentNoteId())
                .setConsignmentNoteDate(data.getConsignmentNoteDate())
                .setPartnerCode(data.getPartnerCode())
                .setTypeOfOperationCode(data.getTypeOfOperationCode())
                .setEmployeeCode(data.getEmployeeCode());

        return consignmentNote;
    }
}
