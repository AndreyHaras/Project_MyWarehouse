package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.Storekeeper;
import org.junit.jupiter.api.Test;
import org.hamcrest.MatcherAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.hamcrest.core.Is.is;

@DataJpaTest
class StorekeeperRepositoryTest extends ActiveProfile {
    @Autowired private StorekeeperRepository repository;

    @Test
    void findByIdTest(){
        Storekeeper actual = repository.findById(2L).get();
        Storekeeper actual2 = repository.findById(3L).get();

        MatcherAssert.assertThat(actual.getSurname(), is("Серюков"));
        MatcherAssert.assertThat(actual2.getSurname(), is("Тамиров"));
    }

    @Test
    void findByEmployeeCodeTest() {
        Storekeeper actual = repository.findByEmployeeCode(13228921).get();

        MatcherAssert.assertThat(actual.getEmployeeCode(), is(13228921));
    }

    @Test
    void findAllRecordsTest(){
        List<Storekeeper> actual = repository.findAll();

        MatcherAssert.assertThat(actual.size(), is(4));
        MatcherAssert.assertThat(actual.get(2).getSurname(), is("Тамиров"));
        MatcherAssert.assertThat(actual.get(1).getEmployeeCode(), is(2215782));
    }
}