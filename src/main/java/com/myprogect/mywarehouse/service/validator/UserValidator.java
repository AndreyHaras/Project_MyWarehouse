package com.myprogect.mywarehouse.service.validator;

import com.myprogect.mywarehouse.service.WarehouseUsersService;
import com.myprogect.mywarehouse.service.dto.WarehouseUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    @Autowired
    WarehouseUsersService service;
    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String NAME_PATTERN = "^[\\p{L} .'-]+$";

    @Override
    public boolean supports(Class<?> aClass) {
        return WarehouseUserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        WarehouseUserDTO userFromPage = (WarehouseUserDTO) o;
        if(userFromPage.getUserName().compareTo("") == 0 ||
                userFromPage.getUserPassword() == null ||
                userFromPage.getUserRole() == null){
            errors.rejectValue("userName", "Error.one.or.more.fields.are.empty");
            return;
        }
        Pattern patternName = Pattern.compile(NAME_PATTERN);
        Matcher matcherName = patternName.matcher(userFromPage.getUserName());
        if(!matcherName.matches()){
            errors.rejectValue("userName", "Error.user.name.is.not.valid");
        }
        WarehouseUserDTO userFromDB = service.findByUserName(userFromPage.getUserName());
        if(userFromDB.getUserName().compareTo("") != 0){
            errors.rejectValue("userName", "NotValid.user.name");
        }
        userFromDB = service.findByUserPassword(passwordEncoder.encode(userFromPage.getUserPassword()));
        if(userFromDB.getUserPassword().compareTo("") != 0){
            errors.rejectValue("userPassword","NotValid.user.password");
        }
    }
}
