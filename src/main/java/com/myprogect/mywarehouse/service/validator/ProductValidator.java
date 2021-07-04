package com.myprogect.mywarehouse.service.validator;

import com.myprogect.mywarehouse.service.ProductMatrixService;
import com.myprogect.mywarehouse.service.dto.ProductMatrixDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ProductValidator implements Validator {
    @Autowired
    ProductMatrixService service;

    private static final String NAME_PATTERN = "^[\\p{L} .'-]+$";

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductMatrixDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductMatrixDTO productFromPage = (ProductMatrixDTO) o;
        if(productFromPage.getProductCode() == null || productFromPage.getProductName().compareTo("") == 0){
            errors.rejectValue("productName", "Error.one.or.more.fields.are.empty");
            return;
        }
        if(productFromPage.getProductCode().toString().length()<2 &&
                productFromPage.getProductCode().toString().length()>10){
            errors.rejectValue("productCode", "Error.value.must.be.integer");
        }
        ProductMatrixDTO productFromDB = service.findByProductName(productFromPage.getProductName());

        if(productFromDB.getProductName().compareTo("") != 0){
            errors.rejectValue("productName", "Error.id.is.already.use");
        }
        productFromDB = service.findByProductCode(productFromPage.getProductCode());
        if(productFromDB.getProductCode().toString().compareTo("0") != 0){
            errors.rejectValue("productCode", "Error.id.is.already.use");
        }
        Pattern patternName = Pattern.compile(NAME_PATTERN);
        Matcher matcher = patternName.matcher(productFromPage.getProductName());
        if(!matcher.matches()){
            errors.rejectValue("productName","NotValid.bank.product.name");
        }
    }
}
