package com.myprogect.mywarehouse.web.controller;

import com.myprogect.mywarehouse.service.BankService;
import com.myprogect.mywarehouse.service.OrganizationAccountService;
import com.myprogect.mywarehouse.service.dto.BankDTO;
import com.myprogect.mywarehouse.service.dto.OrganizationAccountDTO;
import com.myprogect.mywarehouse.service.validator.OrganizationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import java.util.List;
import static com.myprogect.mywarehouse.web.constant.Constants.BaseController.*;

@Controller
@Secured({"ROLE_ADMIN"})
@RequestMapping(ORGANIZATION)
public class OrganizationAccountController {
    @Autowired
    OrganizationAccountService organizationService;
    @Autowired
    BankService bankService;
    @Autowired
    OrganizationValidator validator;

    @RequestMapping(value = LISTING)
    public String showAllAccounts(Model model){
        List<OrganizationAccountDTO> organizationList = organizationService.findAllRecords();
        model.addAttribute("organizationList", organizationList);
        return "organization_page";
    }

    @RequestMapping(value = ADD, method = RequestMethod.GET)
    public String getInformationToAddOrganizationAccount(Model model){
        OrganizationAccountDTO organizationAccountDTO = new OrganizationAccountDTO();
        List<BankDTO> bankInformation = bankService.findAllRecord();
        model.addAttribute("bankList", bankInformation);
        model.addAttribute("organizationAdd", organizationAccountDTO);
        return "organization_add";
    }

    @RequestMapping(value = ADD, method = RequestMethod.POST)
    public String addOrganizationAccount(@Valid @ModelAttribute("organizationAdd") OrganizationAccountDTO organizationFromPage,
                                         BindingResult bindingResult, Model model){
        OrganizationAccountDTO organizationToDB = addOrganizationAccountDTO(organizationFromPage);
        validator.validate(organizationToDB, bindingResult);
        if(bindingResult.hasErrors()){
            List<BankDTO> bankInformation = bankService.findAllRecord();
            model.addAttribute("bankList", bankInformation);
            return "organization_add";
        }

        organizationService.saveDate(organizationToDB);
        return "redirect:" + ORGANIZATION + LISTING;
    }


    private OrganizationAccountDTO addOrganizationAccountDTO(OrganizationAccountDTO organization){
        return new OrganizationAccountDTO()
                .setOrganizationName(organization.getOrganizationName())
                .setSettlementAccount(organization.getSettlementAccount())
                .setCodeOfPayer(organization.getCodeOfPayer())
                .setBankCode(organization.getBankCode());
    }
}
