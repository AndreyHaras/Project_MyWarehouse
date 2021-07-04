package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.OrganizationAccount;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.core.Is.is;

@DataJpaTest
class OrganizationAccountRepositoryTest extends ActiveProfile {
    @Autowired private OrganizationAccountRepository repository;

    @Test
    void findByIdTest(){
        Optional<OrganizationAccount> actual = repository.findById(2L);

        MatcherAssert.assertThat(actual.get().getOrganizationName(), is("счет_организации_2"));
        MatcherAssert.assertThat(actual.get().getCodeOfPayer(), is(398437953L));
    }

    @Test
    void findByOrganizationNameTest() {
        OrganizationAccount actual = repository.findByOrganizationName("счет_организации_2").get();

        MatcherAssert.assertThat(actual.getOrganizationName(), is("счет_организации_2"));
    }

    @Test
    void findByCodeOfPayerTest() {
        OrganizationAccount actual = repository.findByCodeOfPayer(379537953L).get();

        MatcherAssert.assertThat(actual.getCodeOfPayer(), is(379537953L));
    }

    @Test
    void findBySettlementAccountTest() {
        OrganizationAccount actual = repository.findBySettlementAccount(1100987689L).get();

        MatcherAssert.assertThat(actual.getSettlementAccount(), is(1100987689L));
    }

    @Test
    void findAllRecordsTest(){
        List<OrganizationAccount> actual = repository.findAll();

        MatcherAssert.assertThat(actual.size(), is(2));
        MatcherAssert.assertThat(actual.get(1).getOrganizationName(), is("счет_организации_2"));
    }
}