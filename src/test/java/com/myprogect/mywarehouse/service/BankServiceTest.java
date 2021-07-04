package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.Bank;
import com.myprogect.mywarehouse.db.repository.BankRepository;
import com.myprogect.mywarehouse.service.dto.BankDTO;
import com.myprogect.mywarehouse.service.dto.BankWithInformationDTO;
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
class BankServiceTest {
    @MockBean private BankRepository repository;
    @Autowired private BankService service;

    @Test
    void byIdTest() {
        when(repository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(new Bank().setId(1L).setBankName("ЭврикаБанк").setBankCode(32544310490L)));

        BankDTO fromService = service.byId(1L);

        MatcherAssert.assertThat(fromService.getBankName(), is("ЭврикаБанк"));
    }

    @Test
    void saveDataFailTest() {

        doThrow(new RuntimeException()).when(repository).save(new Bank().setBankName("ExceptionBank").setBankCode(1313123L));
        try {
            service.saveData(new BankDTO().setBankName("ExceptionBank").setBankCode(1313123L));
            fail();
        }catch (Exception e){
        }
    }

    @Test
    void findAllRecordTest() {
        when(repository.findAll())
                .thenReturn(new ArrayList<Bank>(){{add(new Bank().setId(1L).setBankName("Bank").setBankCode(1234567L));
                                                add(new Bank().setBankName("Bank2").setBankCode(13213123L));}});

        List<BankDTO> fromService = service.findAllRecord();

        MatcherAssert.assertThat(fromService.size(), is(2));
        MatcherAssert.assertThat(fromService.get(0).getBankName(), is("Bank"));
        MatcherAssert.assertThat(fromService.get(1).getBankCode(), is(13213123L));
    }

    @Test
    void findAllInformationByIdTest() {
        when(repository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(new Bank().setBankName("Bank").setBankCode(12121212L)));

        BankWithInformationDTO fromService = service.findAllInformationById(1L);

        MatcherAssert.assertThat(fromService.getBankName(), is("Bank"));
        MatcherAssert.assertThat(fromService.getBankCode(), is(12121212L));
    }

    @Test
    void findByNameTest() {
        when(repository.findByBankName("Bank"))
                .thenReturn(java.util.Optional.ofNullable(new Bank().setBankName("TestBank")));

        BankDTO fromService = service.findByName("Bank");

        MatcherAssert.assertThat(fromService.getBankName(), is("TestBank"));
    }

    @Test
    void findByCodeTest() {
        when(repository.findByBankCode(11334455L))
                .thenReturn(java.util.Optional.ofNullable(new Bank().setBankCode(888888888L)));
        BankDTO fromService = service.findByCode(11334455L);

        MatcherAssert.assertThat(fromService.getBankCode(), is(888888888L));

    }
}