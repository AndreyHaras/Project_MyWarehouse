package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.ConsignmentNote;
import com.myprogect.mywarehouse.db.entity.Liability;
import com.myprogect.mywarehouse.db.entity.Storekeeper;
import com.myprogect.mywarehouse.db.repository.StorekeeperRepository;
import com.myprogect.mywarehouse.service.dto.StorekeeperDTO;
import com.myprogect.mywarehouse.service.dto.StorekeeperWithInformationDTO;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest()
@TestPropertySource("/application-test-service.properties")
class StorekeeperServiceTest {
    @MockBean
    StorekeeperRepository repository;
    @Autowired
    StorekeeperService service;

    @Test
    void byIdTest() {
        when(repository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(
                        new Storekeeper().setId(1L).setEmployeeCode(889966).setName("Name").setMiddleName("MiddleName")
                                .setEmployeeLiability(new Liability().setLiabilityCode(8899))
                                .setConsignmentNote(new ArrayList<ConsignmentNote>(){{
                                    add(new ConsignmentNote().setId(2L).setConsignmentNoteId(667788L).setConsignmentNoteDate(Date.valueOf("2021-02-27")));}})));
        StorekeeperWithInformationDTO fromService = service.byId(1L);

        MatcherAssert.assertThat(fromService.getName(), is("Name"));
        MatcherAssert.assertThat(fromService.getLiabilityId(), is(8899));
        MatcherAssert.assertThat(fromService.getConsignmentNote().get(0).getConsignmentNoteDate(), is("2021-02-27"));
    }

    @Test
    void saveOrUpdateTest() {
        doThrow(new RuntimeException())
                .when(repository).save(
                        new Storekeeper().setEmployeeCode(8899).setName("Name").setMiddleName("MiddleName"));
        try {
            service.saveOrUpdate(new StorekeeperDTO().setEmployeeCode(8899).setName("Name").setMiddleName("MiddleName"));
            fail();
        }catch (Exception ex){}
    }

    @Test
    void findAllRecordTest() {
        when(repository.findAll()).thenReturn(new ArrayList<Storekeeper>(){{
                    add(new Storekeeper().setId(1L).setName("Name1").setMiddleName("MiddleName1").setEmployeeLiability(new Liability().setLiabilityCode(17892)));
                    add(new Storekeeper().setId(2L).setName("Name2").setMiddleName("MiddleName2").setEmployeeLiability(new Liability().setLiabilityCode(19992)));}});
        List<StorekeeperDTO> fromService = service.findAllRecord();

        MatcherAssert.assertThat(fromService.get(0).getName(), is("Name1"));
        MatcherAssert.assertThat(fromService.get(1).getMiddleName(), is("MiddleName2"));
        MatcherAssert.assertThat(fromService.get(0).getLiabilityId(), is(17892));
    }

    @Test
    void findOnlyNameAndIdTest() {
        when(repository.findAll())
                .thenReturn(new ArrayList<Storekeeper>(){{
                    add(new Storekeeper().setId(1L).setSurname("Name"));
                    add(new Storekeeper().setId(2L).setSurname("Name2"));}});
        List<StorekeeperDTO> fromService = service.findOnlyNameAndId();

        MatcherAssert.assertThat(fromService.get(0).getSurname(), is("Name"));
        MatcherAssert.assertThat(fromService.size(), is(2));
    }

    @Test
    void findByEmployeeCodeTest() {
        when(repository.findByEmployeeCode(889)).thenReturn(java.util.Optional.ofNullable(new Storekeeper().setEmployeeCode(2)));

        StorekeeperDTO fromService = service.findByEmployeeCode(889);

        MatcherAssert.assertThat(fromService.getEmployeeCode(), is(2));
    }
}