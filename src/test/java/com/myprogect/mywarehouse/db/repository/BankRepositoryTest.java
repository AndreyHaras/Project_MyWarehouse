package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.Bank;
import com.myprogect.mywarehouse.service.dto.BankDTO;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.hamcrest.core.Is.is;

@DataJpaTest
public class BankRepositoryTest extends ActiveProfile {
    @Autowired private BankRepository repository;

    @Test
    void findByIdTest(){
        Bank fromDb = repository.findById(1L).get();

        BankDTO actual = new BankDTO()
                .setId(fromDb.getId())
                .setBankCode(fromDb.getBankCode())
                .setBankName(fromDb.getBankName());

        BankDTO expected = new BankDTO()
                .setId(1L)
                .setBankName("ЭврикаБанк")
                .setBankCode(32544310490L);

        MatcherAssert.assertThat(actual, is(expected));
    }

    @Test
    void findByNameTest(){
        Bank actual = repository.findByBankName("СатурнБанк").get();

        MatcherAssert.assertThat(actual.getBankName(), is("СатурнБанк"));
    }

    @Test
    void findByCodeTest(){
        Bank actual = repository.findByBankCode(32544310490L).get();

        MatcherAssert.assertThat(actual.getBankCode(), is(32544310490L));
    }

    @Test
    void findAllRecordTest(){
        List<Bank> actual = repository.findAll();

        MatcherAssert.assertThat(actual.size(), is(2));
        MatcherAssert.assertThat(actual.get(1).getBankName(), is("СатурнБанк"));
    }
}
