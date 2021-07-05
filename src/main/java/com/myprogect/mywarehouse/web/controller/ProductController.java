package com.myprogect.mywarehouse.web.controller;

import com.myprogect.mywarehouse.service.ProductMatrixService;
import com.myprogect.mywarehouse.service.dto.ProductMatrixDTO;
import com.myprogect.mywarehouse.service.validator.ProductValidator;
import com.myprogect.mywarehouse.web.constant.Constants;
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
import static com.myprogect.mywarehouse.web.constant.Constants.BaseController.LISTING;

@Controller
@RequestMapping(Constants.BaseController.PRODUCT)
public class ProductController {
    @Autowired
    ProductMatrixService productService;
    @Autowired
    ProductValidator validator;

    @RequestMapping(value = LISTING)
    public String showAllProducts(Model model){
        ProductMatrixDTO productToAdd = new ProductMatrixDTO();
        List<ProductMatrixDTO> findAllProduct = productService.findAllRecord();
        model.addAttribute("productList",findAllProduct);
        model.addAttribute("productAdd", productToAdd);
        return "product_page";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = Constants.BaseController.ADD, method = RequestMethod.POST)
    public String addProductToDB(@Valid @ModelAttribute("productAdd") ProductMatrixDTO productFromPage,
                                 BindingResult bindingResult, Model model){

        ProductMatrixDTO productToDB = newProductDTO(productFromPage);

        validator.validate(productToDB,bindingResult);

        if(bindingResult.hasErrors()){
            List<ProductMatrixDTO> findAllProduct = productService.findAllRecord();
            model.addAttribute("productList",findAllProduct);
            return "product_page";
        }

        productService.saveProduct(productToDB);

        return "redirect:" + Constants.BaseController.PRODUCT + LISTING;
    }

    private ProductMatrixDTO newProductDTO(ProductMatrixDTO product){
        return new ProductMatrixDTO()
                .setProductCode(product.getProductCode())
                .setProductName(product.getProductName());
    }
}
