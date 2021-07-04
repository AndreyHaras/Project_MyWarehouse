package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.Liability;
import com.myprogect.mywarehouse.db.repository.LiabilityRepository;
import com.myprogect.mywarehouse.service.dto.LiabilityDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import org.hamcrest.MatcherAssert;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest()
@TestPropertySource("/application-test-service.properties")
class LiabilityServiceTest {
    @MockBean
    private LiabilityRepository repository;
    @Autowired
    private LiabilityService service;

    @Test
    void byIdTest() {
        when(repository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(new Liability().setId(1L).setLiabilityCode(12)));

        LiabilityDTO fromService = service.byId(1L);

        MatcherAssert.assertThat(fromService.getLiabilityCode(), is(12));
    }

    @Test
    void findAllRecordTest() {
        when(repository.findAll()).
                thenReturn(new ArrayList<Liability>(){{add(new Liability().setId(1L).setLiabilityCode(2));
                                            add(new Liability().setId(2L).setLiabilityCode(3)); }});
        List<LiabilityDTO> fromService = service.findAllRecord();

        MatcherAssert.assertThat(fromService.size(), is(2));
        MatcherAssert.assertThat(fromService.get(0).getLiabilityCode(), is(2));
    }
}