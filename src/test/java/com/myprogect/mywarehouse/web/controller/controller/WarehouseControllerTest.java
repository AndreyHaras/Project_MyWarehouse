package com.myprogect.mywarehouse.web.controller.controller;


import com.myprogect.mywarehouse.service.WarehouseService;
import com.myprogect.mywarehouse.service.dto.ProductMatrixDTO;
import com.myprogect.mywarehouse.service.dto.WarehouseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import static com.myprogect.mywarehouse.web.controller.constant.Constants.BaseController.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = {WarehouseController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase
class WarehouseControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    WarehouseService service;

    @BeforeEach
    void setUp() {
        when(service.findTotalSumQuantity()).thenReturn(new ArrayList<WarehouseDTO>(){{
            add(new WarehouseDTO()
                    .setId(1L)
                    .setPrice("120.56")
                    .setQuantity("60.98")
                    .setProductMatrix(new ProductMatrixDTO().setProductName("product")));
        }});
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showAllProducts() throws Exception {

        mockMvc.perform(get(WAREHOUSE + LISTING))
                .andExpect(status().isOk())
                .andExpect(view().name("warehouse_page"));
    }
}