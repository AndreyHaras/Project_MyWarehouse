package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.Operations;
import com.myprogect.mywarehouse.db.repository.OperationsRepository;
import com.myprogect.mywarehouse.service.dto.OperationsDTO;
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
class OperationsServiceTest {
    @MockBean private OperationsRepository repository;
    @Autowired private OperationsService service;


    @Test
    void byIdTest() {
        when(repository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(new Operations().setId(1L).setTypeOfOperation("Operation")));

        OperationsDTO fromService = service.byId(1L);

        MatcherAssert.assertThat(fromService.getTypeOfOperation(), is("Operation"));
    }

    @Test
    void findAllRecordTest() {
        when(repository.findAll())
                .thenReturn(new ArrayList<Operations>(){{add(new Operations().setId(1L).setTypeOfOperation("Operation1"));
                                                add(new Operations().setId(2L).setTypeOfOperation("Operations2"));}});
        List<OperationsDTO> fromService = service.findAllRecord();

        MatcherAssert.assertThat(fromService.size(), is(2));
        MatcherAssert.assertThat(fromService.get(0).getTypeOfOperation(), is("Operation1"));

    }
}