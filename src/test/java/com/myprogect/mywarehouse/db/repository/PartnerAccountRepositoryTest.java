package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.PartnerAccount;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.core.Is.is;

@DataJpaTest
class PartnerAccountRepositoryTest extends ActiveProfile {
    @Autowired private PartnerAccountRepository repository;

    @Test
    void findByIdTest(){
        Optional<PartnerAccount> actual = repository.findById(2L);

        MatcherAssert.assertThat(actual.get().getOrganizationName(), is("ПенСолТорг"));
        MatcherAssert.assertThat(actual.get().getPartnerCode(), is(18983L));
    }

    @Test
    void findByPartnerCodeTest() {
        PartnerAccount actual = repository.findByPartnerCode(32983L).get();

        MatcherAssert.assertThat(actual.getPartnerCode(), is(32983L));
    }

    @Test
    void findByOrganizationNameTest() {
        PartnerAccount actual = repository.findByOrganizationName("Палитра").get();

        MatcherAssert.assertThat(actual.getOrganizationName(), is("Палитра"));
    }

    @Test
    void findByCodeOfPayerTest() {
        PartnerAccount actual = repository.findByCodeOfPayer(2386872L).get();

        MatcherAssert.assertThat(actual.getCodeOfPayer(), is(2386872L));
    }

    @Test
    void findBySettlementAccountTest() {
        PartnerAccount actual = repository.findBySettlementAccount(672622364L).get();

        MatcherAssert.assertThat(actual.getSettlementAccount(), is(672622364L));
    }

    @Test
    void findAllRecordsTest(){
        List<PartnerAccount> actual = repository.findAll();

        MatcherAssert.assertThat(actual.size(), is(4));
        MatcherAssert.assertThat(actual.get(0).getSettlementAccount(), is(672622364L));
        MatcherAssert.assertThat(actual.get(1).getOrganizationName(), is("ПенСолТорг"));
        MatcherAssert.assertThat(actual.get(2).getCodeOfPayer(), is(1456872L));
    }
}