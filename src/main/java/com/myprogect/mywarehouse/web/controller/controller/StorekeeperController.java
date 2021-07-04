package com.myprogect.mywarehouse.web.controller.controller;

import com.myprogect.mywarehouse.service.LiabilityService;
import com.myprogect.mywarehouse.service.StorekeeperService;
import com.myprogect.mywarehouse.service.dto.*;
import com.myprogect.mywarehouse.service.validator.StorekeeperValidator;
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
import static com.myprogect.mywarehouse.web.controller.constant.Constants.BaseController.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(STOREKEEPER)
public class StorekeeperController {

    @Autowired
    StorekeeperService storekeeperService;
    @Autowired
    LiabilityService liabilityService;
    @Autowired
    StorekeeperValidator validator;

    @RequestMapping(value = LISTING)
    public String showAllEmployee(Model model){
        StorekeeperDTO storekeeperDTO = new StorekeeperDTO();
        List<StorekeeperDTO> storekeeperList = storekeeperService.findAllRecord();
        model.addAttribute("storekeeperList",storekeeperList);
        model.addAttribute("storekeeperAdd", storekeeperDTO);
        return "storekeeper_page";
    }

    @RequestMapping(value = SHOWINFORMATION + "{id}")
    public String showInformationOfRecord(@PathVariable("id") Long id, Model model){
        StorekeeperWithInformationDTO storekeeperWithInformation = storekeeperService.byId(id);

        if(storekeeperWithInformation == null) throw new ResponseStatusException(NOT_FOUND, "Unable to find id");

        model.addAttribute("storekeeperInfo", storekeeperWithInformation);

        return "storekeeper_info";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = UPDATESTOREKEEPER + "{id}", method = RequestMethod.GET)
    public String getInformationToUpdateStorekeeperRecord(@PathVariable("id") Long id, Model model){
        StorekeeperWithInformationDTO storekeeperWithInformation = storekeeperService.byId(id);

        if(storekeeperWithInformation == null) throw new ResponseStatusException(NOT_FOUND, "Unable to find id");

        List<LiabilityDTO> liability = liabilityService.findAllRecord();

        model.addAttribute("storekeeperUpdate", storekeeperWithInformation);
        model.addAttribute("liabilityList", liability);
        return "storekeeper_update";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = UPDATESTOREKEEPER, method = RequestMethod.POST)
    public String updateStorekeeperRecordInDB(@Valid @ModelAttribute("storekeeperUpdate") StorekeeperDTO storekeeperFromPage,
                                      BindingResult bindingResult, Model model){

        StorekeeperDTO storekeeperToDB = addStorekeeperDTO(storekeeperFromPage);

        validator.validate(storekeeperToDB,bindingResult);

        if(bindingResult.hasErrors()){
            List<LiabilityDTO> liability = liabilityService.findAllRecord();
            model.addAttribute("liabilityList", liability);
            return "storekeeper_update";
        }

        storekeeperService.saveOrUpdate(storekeeperToDB);
        return "redirect:" + STOREKEEPER + LISTING;
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = ADD, method = RequestMethod.GET)
    public String getInformationToAddStorekeeper(Model model){
        StorekeeperDTO newStorekeeper = new StorekeeperDTO();
        List<LiabilityDTO> liability = liabilityService.findAllRecord();
        model.addAttribute("storekeeperAdd", newStorekeeper);
        model.addAttribute("liabilityList", liability);
        return "storekeeper_add";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = ADD, method = RequestMethod.POST)
    public String addStorekeeperToDB(@Valid @ModelAttribute("storekeeperAdd") StorekeeperDTO storekeeperFromPage,
                                     BindingResult bindingResult, Model model){

        StorekeeperDTO storekeeperToDB = addStorekeeperDTO(storekeeperFromPage);

        validator.validate(storekeeperToDB,bindingResult);

        if(bindingResult.hasErrors()){
            List<LiabilityDTO> liability = liabilityService.findAllRecord();
            model.addAttribute("liabilityList", liability);
            return "storekeeper_add";
        }

        storekeeperService.saveOrUpdate(storekeeperToDB);
        return "redirect:" + STOREKEEPER + LISTING;
    }

    private StorekeeperDTO addStorekeeperDTO(StorekeeperDTO storekeeperDTO){
        return new StorekeeperDTO()
                .setId(storekeeperDTO.getId())
                .setLiabilityId(storekeeperDTO.getLiabilityId())
                .setEmployeeCode(storekeeperDTO.getEmployeeCode())
                .setMiddleName(storekeeperDTO.getMiddleName())
                .setName(storekeeperDTO.getName())
                .setSurname(storekeeperDTO.getSurname());
    }
}
