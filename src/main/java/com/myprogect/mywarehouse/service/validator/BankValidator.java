package com.myprogect.mywarehouse.service.validator;

import com.myprogect.mywarehouse.service.BankService;
import com.myprogect.mywarehouse.service.dto.BankDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BankValidator implements Validator {
    @Autowired
    BankService bank;

    private static final String NAME_PATTERN = "^[\\p{L} .'-]+$";

    @Override
    public boolean supports(Class<?> aClass) {
        return BankDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BankDTO bankFromPage = (BankDTO) o;

        if(bankFromPage.getBankCode() == null){
            errors.rejectValue("bankCode","Error.data.is.not.enter");
            return;
        }
        if(bankFromPage.getBankName().compareTo("") == 0){
            errors.rejectValue("bankName","Error.data.is.not.enter");
            return;
        }
        if(bankFromPage.getBankCode().toString().length() < 6 || bankFromPage.getBankCode().toString().length() > 18){
            errors.rejectValue("bankCode", "Error.value.must.be.long");
        }
        BankDTO bankFromDB = bank.findByName(bankFromPage.getBankName());
        if(bankFromDB.getBankName().compareTo("") != 0){
            errors.rejectValue("bankName","Error.id.is.already.use");
        }
        bankFromDB = bank.findByCode(bankFromPage.getBankCode());
        if(bankFromDB.getBankCode().toString().compareTo("0") != 0){
            errors.rejectValue("bankCode","Error.id.is.already.use");
        }
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(bankFromPage.getBankName());
        if(!matcher.matches()){
            errors.rejectValue("bankName","NotValid.bank.name");
        }
    }
}
