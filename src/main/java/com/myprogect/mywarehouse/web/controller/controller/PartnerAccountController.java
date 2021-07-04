package com.myprogect.mywarehouse.web.controller.controller;

import com.myprogect.mywarehouse.service.BankService;
import com.myprogect.mywarehouse.service.PartnerAccountService;
import com.myprogect.mywarehouse.service.dto.*;
import com.myprogect.mywarehouse.service.validator.PartnerValidator;
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
@Secured({"ROLE_ADMIN"})
@RequestMapping(PARTNER)
public class PartnerAccountController {
    @Autowired
    PartnerAccountService partnerService;
    @Autowired
    BankService bankService;
    @Autowired
    PartnerValidator validator;

    @RequestMapping(value = LISTING)
    public String showAllPartnerAccounts(Model model){
        List<PartnerAccountDTO> partnerList = partnerService.findAllRecord();
        model.addAttribute("partnerList", partnerList);
        return "partner_page";
    }

    @RequestMapping(value = SHOWINFORMATION + "{id}")
    public String showInformationOfRecord(@PathVariable("id") Long id, Model model){
        PartnerAccountWithInformationDTO partnerAccountDTO = partnerService.byId(id);
        if(partnerAccountDTO == null) throw new ResponseStatusException(NOT_FOUND, "Unable to find id");

        model.addAttribute("partnerInfo", partnerAccountDTO);
        return "partner_info";
    }

    @RequestMapping(value = ADD, method = RequestMethod.GET)
    public String getInformationToAddOrganizationAccount(Model model){
        PartnerAccountDTO organizationAccountDTO = new PartnerAccountDTO();
        List<BankDTO> bankInformation = bankService.findAllRecord();
        model.addAttribute("bankList", bankInformation);
        model.addAttribute("partnerAdd", organizationAccountDTO);
        return "partner_add";
    }

    @RequestMapping(value = ADD, method = RequestMethod.POST)
    public String addOrganizationAccount(@Valid @ModelAttribute("partnerAdd") PartnerAccountDTO partnerFromPage,
                                         BindingResult bindingResult, Model model){
        PartnerAccountDTO partnerToDB = addPartnerAccountDTO(partnerFromPage);
        validator.validate(partnerToDB, bindingResult);
        if(bindingResult.hasErrors()){
            List<BankDTO> bankInformation = bankService.findAllRecord();
            model.addAttribute("bankList", bankInformation);
            return "partner_add";
        }
        partnerService.saveData(partnerToDB);
        return "redirect:" + PARTNER + LISTING;
    }

    private PartnerAccountDTO addPartnerAccountDTO(PartnerAccountDTO partner){
        return new PartnerAccountDTO()
                .setPartnerCode(partner.getPartnerCode())
                .setOrganizationName(partner.getOrganizationName())
                .setCodeOfPayer(partner.getCodeOfPayer())
                .setSettlementAccount(partner.getSettlementAccount())
                .setBankCode(partner.getBankCode());
    }
}
