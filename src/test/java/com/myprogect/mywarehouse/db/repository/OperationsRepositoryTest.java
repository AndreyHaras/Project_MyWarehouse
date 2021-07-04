package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.Operations;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.core.Is.is;

@DataJpaTest
class OperationsRepositoryTest extends ActiveProfile {
    @Autowired private OperationsRepository repository;

    @Test
    void findByIdTest(){
        Optional<Operations> actual = repository.findById(1L);

        MatcherAssert.assertThat(actual.get().getTypeOfOperation(), is("Приход"));
    }

    @Test
    void findAllRecordsTest(){
        List<Operations> actual = repository.findAll();

        MatcherAssert.assertThat(actual.size(), is(2));
        MatcherAssert.assertThat(actual.get(1).getTypeOfOperation(), is("Возврат"));
    }
}