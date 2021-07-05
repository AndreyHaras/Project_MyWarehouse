package com.myprogect.mywarehouse.web.controller;

import com.myprogect.mywarehouse.service.BankService;
import com.myprogect.mywarehouse.service.PartnerAccountService;
import com.myprogect.mywarehouse.service.dto.BankDTO;
import com.myprogect.mywarehouse.service.dto.PartnerAccountDTO;
import com.myprogect.mywarehouse.service.dto.PartnerAccountWithInformationDTO;
import com.myprogect.mywarehouse.service.validator.PartnerValidator;
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
import static com.myprogect.mywarehouse.web.constant.Constants.BaseController.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = {PartnerAccountController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase
class PartnerAccountControllerTest {
    PartnerAccountDTO partnerAccount;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PartnerAccountService partnerService;
    @MockBean
    BankService bankService;
    @MockBean
    PartnerValidator validator;


    @BeforeEach
    void setUp() {
        partnerAccount = new PartnerAccountDTO()
                .setId(1L)
                .setSettlementAccount(11223344L)
                .setOrganizationName("Organization")
                .setCodeOfPayer(111222L)
                .setPartnerCode(222333L)
                .setBankCode(1L);
        when(partnerService.byId(1L)).thenReturn(
                new PartnerAccountWithInformationDTO()
                        .setId(1L)
                        .setSettlementAccount(21312313L)
                        .setCodeOfPayer(2424234234L)
                        .setOrganizationName("TestOrganization")
                        .setPartnerBank(new BankDTO().setBankCode(124221L)));
        when(bankService.findAllRecord()).thenReturn(new ArrayList<BankDTO>(){{add(
                new BankDTO().setId(1L).setBankCode(113212L).setBankName("TestBank"));}});
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showAllPartnerAccounts() throws Exception {

        mockMvc.perform(get(PARTNER + LISTING))
                .andExpect(status().isOk())
                .andExpect(view().name("partner_page"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showInformationOfRecord() throws Exception {

        mockMvc.perform(get(PARTNER + SHOWINFORMATION + partnerAccount.getId() + "/"))
                .andExpect(status().isOk())
                .andExpect(view().name("partner_info"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showInformationOfRecordNotFound() throws Exception {
        mockMvc.perform(get(PARTNER + SHOWINFORMATION + 100 + "/"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getInformationToAddOrganizationAccount() throws Exception {

        mockMvc.perform(get(PARTNER + ADD))
                .andExpect(status().isOk())
                .andExpect(view().name("partner_add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addOrganizationAccount() throws Exception {

        mockMvc.perform(post(PARTNER + ADD)
                .param("id", "1")
                .param("partnerCode", "11223344")
                .param("organizationName", "Partner")
                .param("codeOfPayer", "1123123")
                .param("settlementAccount", "12313123")
                .param("bankCode", "5553535"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(PARTNER + LISTING));

    }
}