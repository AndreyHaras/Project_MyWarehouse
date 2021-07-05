package com.myprogect.mywarehouse.web.controller;

import com.myprogect.mywarehouse.service.WarehouseUsersService;
import com.myprogect.mywarehouse.service.dto.WarehouseUserDTO;
import com.myprogect.mywarehouse.service.validator.UserValidator;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = {UserController.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase
class UserControllerTest {
    WarehouseUserDTO user1;

    @Autowired
    MockMvc mockMvc;
    @MockBean
    WarehouseUsersService service;
    @MockBean
    UserValidator validator;

    @BeforeEach
    public void setUp(){
        user1 = new WarehouseUserDTO()
                .setId(1L)
                .setUserName("user")
                .setUserPassword("user")
                .setUserRole("ROLE_USER")
                .setAccess(true);

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void userListing() throws Exception {

        mockMvc.perform(get(USERS + LISTING))
                .andExpect(status().isOk())
                .andExpect(view().name("users_page"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void recordDelete() throws Exception{

        mockMvc.perform(get(USERS + DELETE + user1.getId() + "/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(USERS + LISTING));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addUserToDB() throws Exception {

        mockMvc.perform(post(USERS + ADD)
                .param("userName","UserTest")
                .param("UserPassword", "TestPassword")
                .param("UserRole", "ROLE_USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(USERS + LISTING));
    }
}