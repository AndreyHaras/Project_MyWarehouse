package com.myprogect.mywarehouse.service.validator;

import com.myprogect.mywarehouse.service.StorekeeperService;
import com.myprogect.mywarehouse.service.dto.StorekeeperDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StorekeeperValidator implements Validator {
    @Autowired
    StorekeeperService service;

    private static final String NAME_PATTERN = "^[\\p{L} .'-]+$";
    private static final String CODE_PATTERN = "[0-9]+";

    @Override
    public boolean supports(Class<?> aClass) {
        return StorekeeperDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        StorekeeperDTO storekeeperFromPage = (StorekeeperDTO) o;
        if(storekeeperFromPage.getSurname().compareTo("") == 0 || storekeeperFromPage.getName().compareTo("") == 0 ||
            storekeeperFromPage.getMiddleName().compareTo("") == 0){
            errors.rejectValue("surname", "Error.one.or.more.fields.are.empty");
        }
        if(storekeeperFromPage.getEmployeeCode() == null){
            errors.rejectValue("employeeCode", "Error.data.is.not.enter");
            return;
        }
        if(storekeeperFromPage.getEmployeeCode().toString().length()<4 &&
                storekeeperFromPage.getEmployeeCode().toString().length()>10){
            errors.rejectValue("employeeCode", "Error.value.must.be.integer");
        }
        StorekeeperDTO storekeeperFromDB = service.findByEmployeeCode(storekeeperFromPage.getEmployeeCode());
        if(storekeeperFromDB.getEmployeeCode().toString().compareTo("0") != 0){
            errors.rejectValue("employeeCode","NotValid.storekeeper.employee.code");
        }
        Pattern patternCode = Pattern.compile(CODE_PATTERN);
        Matcher matcherCode = patternCode.matcher(storekeeperFromPage.getEmployeeCode().toString());
        if(matcherCode.matches()){
            errors.rejectValue("employeeCode", "NotValid.storekeeper.code");
        }
        Pattern patternName = Pattern.compile(NAME_PATTERN);
        Matcher matcherName = patternName.matcher(storekeeperFromPage.getName());
        if(!matcherName.matches()){
            errors.rejectValue("name","NotValid.storekeeper.name");
        }
        Matcher matcherSurname = patternName.matcher(storekeeperFromPage.getSurname());
        if(!matcherSurname.matches()){
            errors.rejectValue("surname","NotValid.storekeeper.surname");
        }
        Matcher matcherMiddleName = patternName.matcher(storekeeperFromPage.getMiddleName());
        if(!matcherMiddleName.matches()){
            errors.rejectValue("surname","NotValid.storekeeper.middle.name");
        }
    }
}
