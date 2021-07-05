package com.myprogect.mywarehouse.web.controller;

import com.myprogect.mywarehouse.service.*;
import com.myprogect.mywarehouse.service.dto.*;
import com.myprogect.mywarehouse.service.validator.ConsignmentNoteValidator;
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

@WebMvcTest(value = {ConsignmentNoteController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase
class ConsignmentNoteControllerTest {
    ConsignmentNoteDTO note1;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ConsignmentNoteService consignmentNoteService;
    @MockBean
    PartnerAccountService partnerAccountService;
    @MockBean
    StorekeeperService storekeeperService;
    @MockBean
    WarehouseService warehouseService;
    @MockBean
    OperationsService operationsService;
    @MockBean
    ProductMatrixService productMatrixService;
    @MockBean
    ConsignmentNoteValidator noteValidator;

    @BeforeEach
    void setUp() {
        note1 = new ConsignmentNoteDTO()
                .setId(1L)
                .setConsignmentNoteId("1321131")
                .setConsignmentNoteDate("2021-04-23")
                .setPartnerAccount(
                        new PartnerAccountDTO()
                                .setId(1L)
                                .setBankCode(1241321L)
                                .setPartnerCode(12313L)
                                .setCodeOfPayer(223456L))
                .setOperation(
                        new OperationsDTO()
                                .setId(1L)
                                .setTypeOfOperation("Operation"))
                .setStorekeeperCode(
                        new StorekeeperDTO()
                                .setId(1L)
                                .setName("Name")
                                .setMiddleName("MiddleName")
                                .setSurname("Surname")
                                .setEmployeeCode(131312)
                                .setLiabilityId(2))
                .setWarehouses(
                        new ArrayList<WarehouseDTO>(){{add(
                                new WarehouseDTO().setId(1L).setQuantity("200.26").setPrice("100.68").setProductMatrix(
                                        new ProductMatrixDTO().setId(1L).setProductName("Product").setProductCode(141432)));}})
                .setEmployeeCode(145637)
                .setPartnerCode(6678396L)
                .setTypeOfOperationCode(1L);

        when(consignmentNoteService.findAllInformation()).thenReturn(new ArrayList<ConsignmentNoteDTO>(){{add(note1);}});
        when(consignmentNoteService.byId(1L)).thenReturn(note1);
        when(productMatrixService.findAllRecord()).thenReturn(
                new ArrayList<ProductMatrixDTO>(){{
                    add(new ProductMatrixDTO().setId(1L).setProductName("Product").setProductCode(123131));}});
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showAllRecords() throws Exception {
        mockMvc.perform(get(CONSIGNMENT + LISTING))
                .andExpect(status().isOk())
                .andExpect(view().name("main_page"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void filterRecordsFirstCase() throws Exception {
        mockMvc.perform(post(CONSIGNMENT + FILTER)
                .param("consignmentNoteDate", "")
                .param("consignmentNoteId", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(CONSIGNMENT + LISTING));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void filterRecordsSecondCase() throws Exception {
        when(consignmentNoteService.findByIdAndDate("2021-04-23", "1"))
                .thenReturn(
                        new ConsignmentNoteDTO()
                                .setId(1L)
                                .setConsignmentNoteDate("2021-04-23")
                                .setConsignmentNoteId("1313123"));

        mockMvc.perform(post(CONSIGNMENT + FILTER)
                .param("consignmentNoteDate", "2021-04-23")
                .param("consignmentNoteId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("main_page"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void recordDelete() throws Exception {
        mockMvc.perform(get(CONSIGNMENT + DELETE + note1.getId() + "/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(CONSIGNMENT + LISTING));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void recordDeleteNotFound() throws Exception {
        mockMvc.perform(get(CONSIGNMENT + DELETE + "/"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showInformationOfRecord() throws Exception {
        mockMvc.perform(get(CONSIGNMENT + SHOWINFORMATION + 1 + "/"))
                .andExpect(status().isOk())
                .andExpect(view().name("consignment_info"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showInformationOfRecordNotFound() throws Exception {
        mockMvc.perform(get(CONSIGNMENT + SHOWINFORMATION + "/"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addConsignmentNote() throws Exception {
        mockMvc.perform(get(CONSIGNMENT + ADD))
                .andExpect(status().isOk())
                .andExpect(view().name("consignment_add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addNewConsignmentNote() throws Exception {
        mockMvc.perform(post(CONSIGNMENT + ADD)
                .param("consignmentNoteId", "id")
                .param("consignmentNoteDate", "2021-04-23")
                .param("partnerCode", "22315")
                .param("typeOfOperationCode", "2")
                .param("employeeCode", "22141"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(CONSIGNMENT + LISTING));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addProductToConsignmentNote() throws Exception {
        mockMvc.perform(get(CONSIGNMENT + ADDPRODUCT + note1.getId() + "/"))
                .andExpect(status().isOk())
                .andExpect(view().name("consignment_product_add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addProductToConsignmentNoteNotFound() throws Exception {
        mockMvc.perform(get(CONSIGNMENT + ADDPRODUCT + 100 + "/"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addMoreProductToConsignmentNote() throws Exception {
        mockMvc.perform(post(CONSIGNMENT + ADDPRODUCT)
                .param("consignmentId", "1")
                .param("productId", "135311")
                .param("price", "200.86")
                .param("quantity", "80.98"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(CONSIGNMENT + ADDPRODUCT + null));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateConsignmentNote() throws Exception {
        mockMvc.perform(get(CONSIGNMENT + UPDATENOTE + note1.getId() + "/"))
                .andExpect(status().isOk())
                .andExpect(view().name("consignment_update"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateConsignmentNoteNotFound() throws Exception {
        mockMvc.perform(get(CONSIGNMENT + UPDATENOTE + 100 + "/"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateOldConsignmentNote() throws Exception {
        mockMvc.perform(post(CONSIGNMENT + UPDATENOTE)
                .param("consignmentNoteId", "id")
                .param("consignmentNoteDate", "2021-04-23")
                .param("partnerCode", "22315")
                .param("typeOfOperationCode", "2")
                .param("employeeCode", "22141"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(CONSIGNMENT + LISTING));
    }
}