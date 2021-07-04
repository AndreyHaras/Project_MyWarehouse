package com.myprogect.mywarehouse.web.controller.controller;

import com.myprogect.mywarehouse.service.BankService;
import com.myprogect.mywarehouse.service.dto.BankDTO;
import com.myprogect.mywarehouse.service.dto.BankWithInformationDTO;
import com.myprogect.mywarehouse.service.validator.BankValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static com.myprogect.mywarehouse.web.controller.constant.Constants.BaseController.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Controller
@Secured({"ROLE_ADMIN"})
@RequestMapping(BANK)
public class BankController {
    
    @Autowired
    BankService bankService;
    @Autowired
    BankValidator validator;

    @RequestMapping(value = LISTING)
    public String showAllBanks(Model model){
        BankDTO bankAdd = new BankDTO();
        List<BankDTO> bankList = bankService.findAllRecord();
        model.addAttribute("bankList",bankList);
        model.addAttribute("bankAdd", bankAdd);
        return "bank_page";
    }

    @RequestMapping(value = SHOWINFORMATION + "{id}")
    public String showInformationOfBank(@PathVariable("id") Long id, Model model){
        BankWithInformationDTO bankAndInformation = bankService.findAllInformationById(id);

        if(bankAndInformation == null) throw new ResponseStatusException(NOT_FOUND, "Unable to find id");

        model.addAttribute("bank_info", bankAndInformation);
        return "bank_info";
    }

    @RequestMapping(value = ADD, method = RequestMethod.POST)
    public String addBankToBD(@Valid @ModelAttribute("bankAdd") BankDTO bank,
                              BindingResult bindingResult, Model model){
        BankDTO bankToDB = new BankDTO();
        bankToDB.setBankCode(bank.getBankCode())
                .setBankName(bank.getBankName());
        validator.validate(bankToDB,bindingResult);
        if(bindingResult.hasErrors()){
            List<BankDTO> bankList = bankService.findAllRecord();
            model.addAttribute("bankList",bankList);
            return "bank_page";
        }
        bankService.saveData(bankToDB);
        return "redirect:" + BANK + LISTING;
    }

}
