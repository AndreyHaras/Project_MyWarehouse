package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.WarehouseUsers;
import com.myprogect.mywarehouse.db.repository.WarehouseUserRepository;
import com.myprogect.mywarehouse.service.dto.WarehouseUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.hamcrest.MatcherAssert;
import org.springframework.test.context.TestPropertySource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest()
@TestPropertySource("/application-test-service.properties")
class WarehouseUsersServiceTest {
    @MockBean
    WarehouseUserRepository repository;
    @Autowired
    WarehouseUsersService service;

    @Test
    void byIdTest() {
        when(repository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(
                        new WarehouseUsers().setId(1L).setUserName("User").setUserPassword("Password")
                                .setUserRole("ROLE_USER")));
        WarehouseUserDTO fromService = service.byId(1L);

        MatcherAssert.assertThat(fromService.getUserName(), is("User"));
        MatcherAssert.assertThat(fromService.getUserRole(), is("ROLE_USER"));
    }

    @Test
    void findAllUsersTest() {
        when(repository.findAll()).thenReturn(new ArrayList<WarehouseUsers>(){{
            add(new WarehouseUsers().setId(1L).setUserName("User").setUserPassword("Password").setUserRole("ROLE_USER"));
            add(new WarehouseUsers().setId(2L).setUserName("Admin").setUserPassword("Wordpass").setUserRole("ROLE_ADMIN"));
        }});
        List<WarehouseUserDTO> fromService = service.findAllUsers();

        MatcherAssert.assertThat(fromService.size(), is(2));
        MatcherAssert.assertThat(fromService.get(1).getUserRole(), is("ROLE_ADMIN"));
        MatcherAssert.assertThat(fromService.get(0).getUserPassword(), is("Password"));
    }

    @Test
    void saveDataTest() {
        doThrow(new RuntimeException()).when(repository)
                .save(new WarehouseUsers().setUserName("User").setUserPassword("EE11CBB19052E40B07AAC0CA060C23EE").setUserRole("ROLE_USER").setAccess(true));
        try{
            service.saveData(new WarehouseUserDTO().setUserName("User").setUserPassword("user").setUserRole("ROLE_USER").setAccess(true));
            fail();
        }catch (Exception ex){}
    }

    @Test
    void findByUserNameTest() {
        when(repository.findByUserName("User")).thenReturn(java.util.Optional.ofNullable(new WarehouseUsers().setUserName("UserTest")));

        WarehouseUserDTO fromService = service.findByUserName("User");

        MatcherAssert.assertThat(fromService.getUserName(), is("UserTest"));
    }

    @Test
    void findByUserPasswordTest() {
        when(repository.findByUserPassword("Password")).thenReturn(java.util.Optional.ofNullable(new WarehouseUsers().setUserPassword("UserPassword")));

        WarehouseUserDTO fromService = service.findByUserPassword("Password");

        MatcherAssert.assertThat(fromService.getUserPassword(), is("UserPassword"));
    }
}