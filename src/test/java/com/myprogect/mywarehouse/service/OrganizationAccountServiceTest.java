package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.Bank;
import com.myprogect.mywarehouse.db.entity.OrganizationAccount;
import com.myprogect.mywarehouse.db.repository.OrganizationAccountRepository;
import com.myprogect.mywarehouse.service.dto.OrganizationAccountDTO;
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
class OrganizationAccountServiceTest {
    @MockBean
    private OrganizationAccountRepository repository;
    @Autowired
    private OrganizationAccountService service;

    @Test
    void byIdTest() {
        when(repository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(
                        new OrganizationAccount().setId(1L).setOrganizationName("Organization").setCodeOfPayer(11223344L)
                                .setBank_id(1L).setSettlementAccount(22445566L).setOrganizationBank(new Bank().setBankCode(1133L))));
        OrganizationAccountDTO fromService = service.byId(1L);

        MatcherAssert.assertThat(fromService.getOrganizationName(), is("Organization"));
        MatcherAssert.assertThat(fromService.getCodeOfPayer(), is(11223344L));
    }

    @Test
    void findAllRecordsTest() {
        when(repository.findAll()).thenReturn(new ArrayList<OrganizationAccount>(){{add(new OrganizationAccount()
                .setId(1L).setOrganizationName("Organization").setCodeOfPayer(11223344L)
                .setBank_id(1L).setSettlementAccount(22445566L)
                .setOrganizationBank(new Bank().setBankCode(1133L)));
                add(new OrganizationAccount().setId(2L).setOrganizationName("Organization2").setCodeOfPayer(44223344L)
                        .setBank_id(1L).setSettlementAccount(11885566L)
                        .setOrganizationBank(new Bank().setBankCode(2266L)));}});

        List<OrganizationAccountDTO> fromService = service.findAllRecords();

        MatcherAssert.assertThat(fromService.size(), is(2));
        MatcherAssert.assertThat(fromService.get(0).getOrganizationName(), is("Organization"));
        MatcherAssert.assertThat(fromService.get(1).getSettlementAccount(), is(11885566L));
    }

    @Test
    void saveDateTest() {
        doThrow(new RuntimeException()).when(repository)
                .save(new OrganizationAccount().setOrganizationName("Organization")
                        .setCodeOfPayer(889977L).setBank_id(1L));
        try{
            service.saveDate(new OrganizationAccountDTO().setOrganizationName("Organization")
                    .setCodeOfPayer(889977L).setBankCode(1L));
            fail();
        }catch (Exception ex){}
    }

    @Test
    void findByOrganizationNameTest() {
        when(repository.findByOrganizationName("Organization")).thenReturn(java.util.Optional.ofNullable(
                new OrganizationAccount().setOrganizationName("OrganizationTest")));

        OrganizationAccountDTO fromService = service.findByOrganizationName("Organization");

        MatcherAssert.assertThat(fromService.getOrganizationName(), is("OrganizationTest"));
    }

    @Test
    void findByCodeOfPayerTest() {
        when(repository.findByCodeOfPayer(1122334455L)).thenReturn(java.util.Optional.ofNullable(
                new OrganizationAccount().setCodeOfPayer(44556677L)));

        OrganizationAccountDTO fromService = service.findByCodeOfPayer(1122334455L);

        MatcherAssert.assertThat(fromService.getCodeOfPayer(), is(44556677L));
    }

    @Test
    void findBySettlementAccountTest() {
        when(repository.findBySettlementAccount(77889900L)).thenReturn(java.util.Optional.ofNullable(
                new OrganizationAccount().setSettlementAccount(44886677L)));

        OrganizationAccountDTO fromService = service.findBySettlementAccount(77889900L);

        MatcherAssert.assertThat(fromService.getSettlementAccount(), is(44886677L));
    }
}