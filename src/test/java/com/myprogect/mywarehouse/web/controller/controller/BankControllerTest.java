package com.myprogect.mywarehouse.web.controller.controller;

import com.myprogect.mywarehouse.service.BankService;
import com.myprogect.mywarehouse.service.dto.BankDTO;
import com.myprogect.mywarehouse.service.dto.BankWithInformationDTO;
import com.myprogect.mywarehouse.service.validator.BankValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static com.myprogect.mywarehouse.web.controller.constant.Constants.BaseController.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = {BankController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase
class BankControllerTest {
    BankDTO bank1;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BankService service;
    @MockBean
    BankValidator bankValidator;

    @BeforeEach
    public void setUp(){
          bank1 = new BankDTO()
                .setId(1L)
                .setBankCode(12321323L)
                .setBankName("Bank1");
          when(service.findAllInformationById(1L)).thenReturn(
                  new BankWithInformationDTO().setId(1L).setBankName("TestBank")
                          .setBankCode(12312312L));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showAllBanks() throws Exception {

        mockMvc.perform(get(BANK + LISTING))
                .andExpect(status().isOk())
                .andExpect(view().name("bank_page"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showInformationOfBank() throws Exception {

        mockMvc.perform(get(BANK + SHOWINFORMATION + bank1.getId() + "/"))
                .andExpect(status().isOk())
                .andExpect(view().name("bank_info"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showInformationOfBankNotFound() throws Exception {

        mockMvc.perform(get(BANK + SHOWINFORMATION + 100 + "/"))
                .andExpect(status().is4xxClientError());
    }



    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addBankToBD() throws Exception {

        mockMvc.perform(post(BANK + ADD)
                .param("bankCode", "12313123")
                .param("bankName", "BankTest"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(BANK + LISTING));

    }
}