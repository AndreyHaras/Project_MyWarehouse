package com.myprogect.mywarehouse.web.controller;

import com.myprogect.mywarehouse.service.LiabilityService;
import com.myprogect.mywarehouse.service.StorekeeperService;
import com.myprogect.mywarehouse.service.dto.StorekeeperDTO;
import com.myprogect.mywarehouse.service.dto.StorekeeperWithInformationDTO;
import com.myprogect.mywarehouse.service.validator.StorekeeperValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static com.myprogect.mywarehouse.web.constant.Constants.BaseController.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = {StorekeeperController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase
class StorekeeperControllerTest {
    StorekeeperDTO storekeeper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    StorekeeperService storekeeperService;
    @MockBean
    LiabilityService liabilityService;
    @MockBean
    StorekeeperValidator validator;

    @BeforeEach
    void setUp() {
        storekeeper = new StorekeeperDTO()
                .setId(1L)
                .setEmployeeCode(121212)
                .setName("name")
                .setSurname("surname")
                .setMiddleName("middlename")
                .setLiabilityId(1);
        when(storekeeperService.byId(1L)).thenReturn(
                new StorekeeperWithInformationDTO()
                        .setId(1L)
                        .setEmployeeCode(121212)
                        .setName("Name")
                        .setSurname("Surname")
                        .setMiddleName("Middlename")
                        .setLiabilityId(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showAllEmployee() throws Exception {

        mockMvc.perform(get(STOREKEEPER + LISTING))
                .andExpect(status().isOk())
                .andExpect(view().name("storekeeper_page"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showInformationOfRecord() throws Exception {

        mockMvc.perform(get(STOREKEEPER + SHOWINFORMATION + storekeeper.getId() + "/"))
                .andExpect(status().isOk())
                .andExpect(view().name("storekeeper_info"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showInformationOfRecordNotFound() throws Exception {

        mockMvc.perform(get(STOREKEEPER + SHOWINFORMATION + 100 + "/"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getInformationToUpdateStorekeeperRecord() throws Exception {

        mockMvc.perform(get(STOREKEEPER + UPDATESTOREKEEPER + storekeeper.getId() + "/"))
                .andExpect(status().isOk())
                .andExpect(view().name("storekeeper_update"));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getInformationToUpdateStorekeeperRecordNotFound() throws Exception {

        mockMvc.perform(get(STOREKEEPER + UPDATESTOREKEEPER + 100 + "/"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateStorekeeperRecordInDB() throws Exception {

        mockMvc.perform(post(STOREKEEPER + UPDATESTOREKEEPER)
                .param("id", "1")
                .param("liabilityId", "1")
                .param("employeeCode", "131321")
                .param("name", "Name")
                .param("surname", "Surname")
                .param("middleName", "MiddleName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(STOREKEEPER + LISTING));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getInformationToAddStorekeeper() throws Exception {
        mockMvc.perform(get(STOREKEEPER + ADD))
                .andExpect(status().isOk())
                .andExpect(view().name("storekeeper_add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addStorekeeperToDB() throws Exception {
        mockMvc.perform(post(STOREKEEPER + ADD)
                .param("id", "1")
                .param("liabilityId", "1")
                .param("employeeCode", "131321")
                .param("name", "Name")
                .param("surname", "Surname")
                .param("middleName", "MiddleName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(STOREKEEPER + LISTING));
    }
}