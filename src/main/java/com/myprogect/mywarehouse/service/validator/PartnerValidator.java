package com.myprogect.mywarehouse.service.validator;

import com.myprogect.mywarehouse.service.PartnerAccountService;
import com.myprogect.mywarehouse.service.dto.PartnerAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PartnerValidator implements Validator {
    @Autowired
    PartnerAccountService service;

    private static final String NAME_PATTERN = "^[\\p{L} .'-]+$";

    @Override
    public boolean supports(Class<?> aClass) {
        return PartnerAccountDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PartnerAccountDTO partnerFromPage = (PartnerAccountDTO) o;
        if(partnerFromPage.getOrganizationName().compareTo("") == 0 ||
            partnerFromPage.getCodeOfPayer() == null || partnerFromPage.getSettlementAccount() == null ||
            partnerFromPage.getPartnerCode() == null){
            errors.rejectValue("partnerCode","Error.one.or.more.fields.are.empty");
            return;
        }
        if(partnerFromPage.getPartnerCode().toString().length() < 4 &&
                partnerFromPage.getPartnerCode().toString().length() > 17){
            errors.rejectValue("partnerCode","Error.value.must.be.long");
        }
        if(partnerFromPage.getCodeOfPayer().toString().length() < 6 &&
                partnerFromPage.getCodeOfPayer().toString().length() > 17){
            errors.rejectValue("codeOfPayer", "Error.value.must.be.long");
        }
        if(partnerFromPage.getSettlementAccount().toString().length() < 6 &&
                partnerFromPage.getSettlementAccount().toString().length() > 17){
            errors.rejectValue("settlementAccount", "Error.value.must.be.long");
        }
        PartnerAccountDTO partnerAccountFromDB = service.findByOrganizationName(partnerFromPage.getOrganizationName());
        if(partnerAccountFromDB.getOrganizationName().compareTo("") != 0){
            errors.rejectValue("organizationName", "NotValid.organization.name");
        }
        Pattern patternName = Pattern.compile(NAME_PATTERN);
        Matcher matcherName = patternName.matcher(partnerFromPage.getOrganizationName());
        if(!matcherName.matches()){
            errors.rejectValue("organizationName","Error.organization.name.is.not.valid");
        }
        partnerAccountFromDB = service.findByPartnerCode(partnerFromPage.getPartnerCode());
        if(partnerAccountFromDB.getPartnerCode() != 0){
            errors.rejectValue("partnerCode","NotValid.partner.code.is.not.valid");
        }
        partnerAccountFromDB = service.findByCodeOfPayer(partnerFromPage.getCodeOfPayer());
        if(partnerAccountFromDB.getCodeOfPayer() != 0){
            errors.rejectValue("codeOfPayer", "NotValid.organization.code.of.payer");
        }
        partnerAccountFromDB = service.findBySettlementAccount(partnerAccountFromDB.getSettlementAccount());
        if(partnerAccountFromDB.getSettlementAccount() != 0){
            errors.rejectValue("codeOfPayer", "NotValid.organization.settlement.account");
        }
        if(partnerFromPage.getBankCode() == null){
            errors.rejectValue("codeOfPayer", "Error.data.is.not.enter");
        }
    }
}
