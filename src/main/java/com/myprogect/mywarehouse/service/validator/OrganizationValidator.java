package com.myprogect.mywarehouse.service.validator;

import com.myprogect.mywarehouse.service.OrganizationAccountService;
import com.myprogect.mywarehouse.service.dto.OrganizationAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OrganizationValidator implements Validator {
    @Autowired
    OrganizationAccountService service;

    private static final String NAME_PATTERN = "^[\\p{L} .'-]+$";

    @Override
    public boolean supports(Class<?> aClass) {
        return OrganizationAccountDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrganizationAccountDTO organizationFromPage = (OrganizationAccountDTO) o;
        if(organizationFromPage.getOrganizationName().compareTo("")==0 ||
                organizationFromPage.getCodeOfPayer() == null || organizationFromPage.getSettlementAccount() == null){
            errors.rejectValue("organizationName","Error.one.or.more.fields.are.empty");
            return;
        }
        if(organizationFromPage.getCodeOfPayer().toString().length() < 6 ||
                organizationFromPage.getCodeOfPayer().toString().length() > 17){
            errors.rejectValue("codeOfPayer", "Error.value.must.be.long");
        }
        if(organizationFromPage.getSettlementAccount().toString().length() <6 ||
                organizationFromPage.getCodeOfPayer().toString().length() > 17){
            errors.rejectValue("settlementAccount", "Error.value.must.be.long");
        }
        OrganizationAccountDTO organizationFromDB = service.findByOrganizationName(organizationFromPage.getOrganizationName());
        if(organizationFromDB.getOrganizationName().compareTo("") != 0){
            errors.rejectValue("organizationName", "NotValid.organization.name");
        }
        Pattern patternName = Pattern.compile(NAME_PATTERN);
        Matcher matcherName = patternName.matcher(organizationFromPage.getOrganizationName());
        if(!matcherName.matches()){
            errors.rejectValue("organizationName","Error.organization.name.is.not.valid");
        }
        organizationFromDB = service.findByCodeOfPayer(organizationFromPage.getCodeOfPayer());
        if(organizationFromDB.getCodeOfPayer().toString().compareTo("0") != 0){
            errors.rejectValue("codeOfPayer", "NotValid.organization.code.of.payer");
        }
        organizationFromDB = service.findBySettlementAccount(organizationFromPage.getSettlementAccount());
        if(organizationFromDB.getCodeOfPayer().toString().compareTo("0") != 0){
            errors.rejectValue("codeOfPayer", "NotValid.organization.settlement.account");
        }
    }
}
