package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.UserRole;
import com.myprogect.mywarehouse.db.entity.WarehouseUsers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.core.Is.is;

@DataJpaTest
class UserRepositoryTest extends ActiveProfile {

    @Autowired private WarehouseUserRepository repository;

    @Test
    void findByUserNameTest() {
        Optional<WarehouseUsers> actual1 = repository.findByUserName("сотрудник");
        Optional<WarehouseUsers> actual2 = repository.findByUserName("админ");


        MatcherAssert.assertThat(actual1.get().getUserName(), is("сотрудник"));
        MatcherAssert.assertThat(actual2.get().getUserName(), is("админ"));
    }


    @Test
    void findByUserPasswordTest() {
        Optional<WarehouseUsers> actual1 = repository.findByUserPassword("user");
        Optional<WarehouseUsers> actual2 = repository.findByUserPassword("admin");


        MatcherAssert.assertThat(actual1.get().getUserPassword(), is("user"));
        MatcherAssert.assertThat(actual2.get().getUserPassword(), is("admin"));
    }
    @Test
    void findByIdTest(){
        Optional<WarehouseUsers> actual = repository.findById(1L);

        WarehouseUsers expected = new WarehouseUsers()
                .setId(1L)
                .setUserName("сотрудник")
                .setUserPassword("user")
                .setUserRole("ROLE_USER")
                .setAccess(true);

        MatcherAssert.assertThat(actual.get(), is(expected));
    }
    @Test
    void findAllRecordsTest(){
        List<WarehouseUsers> actual = repository.findAll();

        MatcherAssert.assertThat(actual.size(), is(2));
        MatcherAssert.assertThat(actual.get(1).getUserRole(), is("ROLE_ADMIN"));
    }
}
