package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.Bank;
import com.myprogect.mywarehouse.db.entity.PartnerAccount;
import com.myprogect.mywarehouse.db.repository.PartnerAccountRepository;
import com.myprogect.mywarehouse.service.dto.PartnerAccountDTO;
import com.myprogect.mywarehouse.service.dto.PartnerAccountWithInformationDTO;
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
class PartnerAccountServiceTest {
    @MockBean
    PartnerAccountRepository repository;
    @Autowired
    PartnerAccountService service;

    @Test
    void byIdTest() {
        when(repository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(
                        new PartnerAccount().setId(1L).setOrganizationName("Partner").setCodeOfPayer(11223344L)
                                .setSettlementAccount(22445566L).setPartnerBank(new Bank().setBankCode(1133L))));
        PartnerAccountWithInformationDTO fromService = service.byId(1L);

        MatcherAssert.assertThat(fromService.getOrganizationName(), is("Partner"));
        MatcherAssert.assertThat(fromService.getCodeOfPayer(), is(11223344L));
    }

    @Test
    void findAllRecordTest() {
        when(repository.findAll()).thenReturn(new ArrayList<PartnerAccount>(){{add(new PartnerAccount()
                .setId(1L).setOrganizationName("Partner").setCodeOfPayer(11223344L).setPartnerCode(11778844L)
                .setSettlementAccount(22445566L).setCodeOfPayer(88997755L).setPartnerBank(new Bank().setBankCode(8899L)));
            add(new PartnerAccount().setId(2L).setOrganizationName("Partner2").setCodeOfPayer(44223344L).setPartnerCode(11778844L)
                    .setSettlementAccount(22445566L).setCodeOfPayer(88997755L).setPartnerBank(new Bank().setBankCode(9900L)));}});

        List<PartnerAccountDTO> fromService = service.findAllRecord();

        MatcherAssert.assertThat(fromService.size(), is(2));
        MatcherAssert.assertThat(fromService.get(1).getOrganizationName(), is("Partner2"));
        MatcherAssert.assertThat(fromService.get(1).getSettlementAccount(), is(22445566L));
        MatcherAssert.assertThat(fromService.get(0).getBankCode(), is(8899L));
    }

    @Test
    void saveDataTest() {
        doThrow(new RuntimeException()).when(repository)
                .save(new PartnerAccount().setOrganizationName("Partner")
                        .setCodeOfPayer(889977L).setBank_id(1L));
        try{
            service.saveData(new PartnerAccountDTO().setOrganizationName("Partner")
                    .setCodeOfPayer(889977L).setBankCode(1L));
            fail();
        }catch (Exception ex){}
    }

    @Test
    void findOnlyNameAndIdTest() {
        when(repository.findAll()).thenReturn(new ArrayList<PartnerAccount>(){{
            add(new PartnerAccount().setOrganizationName("PartnerTest").setId(1L));
            add(new PartnerAccount().setOrganizationName("PartnerTest2").setId(2L));}});

        List<PartnerAccountDTO> fromService = service.findOnlyNameAndId();

        MatcherAssert.assertThat(fromService.get(0).getOrganizationName(), is("PartnerTest"));
        MatcherAssert.assertThat(fromService.size(), is(2));
    }

    @Test
    void findByPartnerCodeTest() {
        when(repository.findByPartnerCode(228899L)).thenReturn(java.util.Optional.ofNullable(
                new PartnerAccount().setPartnerCode(9087654L)));

        PartnerAccountDTO fromService = service.findByPartnerCode(228899L);

        MatcherAssert.assertThat(fromService.getPartnerCode(), is(9087654L));
    }

    @Test
    void findByOrganizationNameTest() {
        when(repository.findByOrganizationName("Partner")).thenReturn(java.util.Optional.ofNullable(
                new PartnerAccount().setOrganizationName("TestPartner")));

        PartnerAccountDTO fromService = service.findByOrganizationName("Partner");

        MatcherAssert.assertThat(fromService.getOrganizationName(), is("TestPartner"));
    }

    @Test
    void findByCodeOfPayerTest() {
        when(repository.findByCodeOfPayer(228899L)).thenReturn(java.util.Optional.ofNullable(
                new PartnerAccount().setCodeOfPayer(9087654L)));
        PartnerAccountDTO fromService = service.findByCodeOfPayer(228899L);
        MatcherAssert.assertThat(fromService.getCodeOfPayer(), is(9087654L));
    }

    @Test
    void findBySettlementAccountTest() {
        when(repository.findBySettlementAccount(228899L)).thenReturn(java.util.Optional.ofNullable(
                new PartnerAccount().setSettlementAccount(9087654L)));
        PartnerAccountDTO fromService = service.findBySettlementAccount(228899L);
        MatcherAssert.assertThat(fromService.getSettlementAccount(), is(9087654L));

    }
}