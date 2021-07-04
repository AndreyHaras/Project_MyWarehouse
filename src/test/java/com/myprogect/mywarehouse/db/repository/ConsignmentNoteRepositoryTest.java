package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.ConsignmentNote;
import org.junit.jupiter.api.Test;
import org.hamcrest.MatcherAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.sql.Date;
import java.util.List;
import static org.hamcrest.core.Is.is;

@DataJpaTest
class ConsignmentNoteRepositoryTest extends ActiveProfile {
    @Autowired private ConsignmentNoteRepository repository;

    @Test
    void findByIdTest(){
        ConsignmentNote actual = repository.findById(2L).get();

        MatcherAssert.assertThat(actual.getConsignmentNoteId(), is(2244356L));
        MatcherAssert.assertThat(actual.getOperation().getTypeOfOperation(), is("Приход"));
    }

    @Test
    void findAllRecordsTest(){
        List<ConsignmentNote> actual = repository.findAll();

        MatcherAssert.assertThat(actual.get(1).getConsignmentNoteId(), is(2244356L));
        MatcherAssert.assertThat(actual.get(2).getStorekeeper().getSurname(), is("Иванов"));
    }

    @Test
    void findByConsignmentNoteDateTest() {
        List<ConsignmentNote> actual = repository.findByConsignmentNoteDate(Date.valueOf("2021-04-23"));

        MatcherAssert.assertThat(actual.get(1).getConsignmentNoteDate(), is(Date.valueOf("2021-04-23")));
    }

    @Test
    void findByConsignmentNoteIdTest() {
        ConsignmentNote actual = repository.findByConsignmentNoteId(7786323L).get();

        MatcherAssert.assertThat(actual.getConsignmentNoteId(), is(7786323L));
    }

    @Test
    void findByConsignmentNoteIdAndAndConsignmentNoteDateTest() {
        ConsignmentNote actual = repository
                .findByConsignmentNoteIdAndAndConsignmentNoteDate(7786323L,Date.valueOf("2021-04-03")).get();

        MatcherAssert.assertThat(actual.getConsignmentNoteDate(), is(Date.valueOf("2021-04-03")));
        MatcherAssert.assertThat(actual.getConsignmentNoteId(), is(7786323L));
    }
}