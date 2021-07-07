package com.myprogect.mywarehouse.service.validator;

import com.myprogect.mywarehouse.service.ConsignmentNoteService;
import com.myprogect.mywarehouse.service.dto.ConsignmentNoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ConsignmentNoteValidator implements Validator {
    @Autowired
    ConsignmentNoteService service;

    private static final String CODE_PATTERN = "[0-9]+";

    @Override
    public boolean supports(Class<?> aClass) {
        return ConsignmentNoteDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ConsignmentNoteDTO noteFromPage = (ConsignmentNoteDTO) o;

        if(noteFromPage.getConsignmentNoteId().compareTo("") == 0){
            errors.rejectValue("consignmentNoteId","Error.data.is.not.enter");
            return;
        }
        if(noteFromPage.getConsignmentNoteDate().compareTo("") == 0){
            errors.rejectValue("consignmentNoteDate", "Data.is.empty");
            return;
        }
        Pattern pattern = Pattern.compile(CODE_PATTERN);
        Matcher matcher = pattern.matcher(noteFromPage.getConsignmentNoteId());
        if(!matcher.matches()){
            errors.rejectValue("consignmentNoteId","Error.organization.code.of.payer");
            return;
        }
        if(noteFromPage.getConsignmentNoteId().length() < 6 || noteFromPage.getConsignmentNoteId().length() > 18){
            errors.rejectValue("consignmentNoteId", "Error.value.must.be.long");
        }
        ConsignmentNoteDTO noteFromDB =
                service.findByConsignmentNoteId(Long.valueOf(noteFromPage.getConsignmentNoteId()));

        if(noteFromDB.getConsignmentNoteId().compareTo("0") != 0){
            errors.rejectValue("consignmentNoteId","Error.id.is.already.use");
        }
        if(noteFromPage.getPartnerAccount() == null){
            errors.rejectValue("consignmentNoteId","Error.data.is.not.enter");
            return;
        }
        if(noteFromPage.getStorekeeperCode() == null){
            errors.rejectValue("consignmentNoteId","Error.data.is.not.enter");
        }
    }
}
