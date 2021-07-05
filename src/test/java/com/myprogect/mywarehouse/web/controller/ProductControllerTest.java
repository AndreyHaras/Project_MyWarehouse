package com.myprogect.mywarehouse.web.controller;

import com.myprogect.mywarehouse.service.ProductMatrixService;
import com.myprogect.mywarehouse.service.validator.ProductValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static com.myprogect.mywarehouse.web.constant.Constants.BaseController.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = {ProductController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ProductMatrixService productService;
    @MockBean
    ProductValidator validator;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showAllProducts() throws Exception {
        mockMvc.perform(get(PRODUCT + LISTING))
                .andExpect(status().isOk())
                .andExpect(view().name("product_page"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addProductToDB() throws Exception {
        mockMvc.perform(post(PRODUCT + ADD)
                .param("productCode", "2312313")
                .param("productName", "ProductTest"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(PRODUCT + LISTING));
    }
}