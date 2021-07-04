package com.myprogect.mywarehouse.web.controller.controller;

import com.myprogect.mywarehouse.service.BankService;
import com.myprogect.mywarehouse.service.OrganizationAccountService;
import com.myprogect.mywarehouse.service.dto.BankDTO;
import com.myprogect.mywarehouse.service.dto.OrganizationAccountDTO;
import com.myprogect.mywarehouse.service.validator.OrganizationValidator;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = {OrganizationAccountController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase
class OrganizationAccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrganizationAccountService organizationService;
    @MockBean
    BankService bankService;
    @MockBean
    OrganizationValidator validator;

    @BeforeEach
    void setUp() {
        when(organizationService.byId(1L)).thenReturn(
                new OrganizationAccountDTO()
                        .setId(2L)
                        .setSettlementAccount(1212112L)
                        .setCodeOfPayer(13112313L)
                        .setOrganizationName("TestOrganization").setBankCode(1L));
        when(bankService.findAllRecord()).thenReturn(new ArrayList<BankDTO>(){{add(
                new BankDTO().setId(1L).setBankCode(113212L).setBankName("TestBank"));}});

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showAllAccounts() throws Exception {

        mockMvc.perform(get(ORGANIZATION + LISTING))
                .andExpect(status().isOk())
                .andExpect(view().name("organization_page"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getInformationToAddOrganizationAccount() throws Exception {

        mockMvc.perform(get(ORGANIZATION + ADD))
                .andExpect(status().isOk())
                .andExpect(view().name("organization_add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addOrganizationAccount() throws Exception {

        mockMvc.perform(post(ORGANIZATION + ADD)
                .param("id", "2")
                .param("organizationName", "OrganizationTest")
                .param("codeOfPayer", "12123131")
                .param("settlementAccount", "44353535")
                .param("bankCode", "3656456456")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ORGANIZATION + LISTING));
    }
}