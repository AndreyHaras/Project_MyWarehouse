package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.Warehouse;
import com.myprogect.mywarehouse.service.search.WarehouseSumGroup;
import org.junit.jupiter.api.Test;
import org.hamcrest.MatcherAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.hamcrest.core.Is.is;

@DataJpaTest
class WarehouseRepositoryTest extends ActiveProfile {
    @Autowired private WarehouseRepository repository;

    @Test
    void findByIdTest(){
        Warehouse actual = repository.findById(1L).get();
        Warehouse actual2 = repository.findById(5L).get();

        MatcherAssert.assertThat(actual.getQuantity(), is(60.96));
        MatcherAssert.assertThat(actual2.getPrice(), is(108.43));
    }

    @Test
    void findByConsignmentIdTest() {
        List<Warehouse> actual = repository.findByConsignmentId(1L);
        List<Warehouse> actual2 = repository.findByConsignmentId(5L);

        MatcherAssert.assertThat(actual.get(0).getPrice(), is(108.43));
        MatcherAssert.assertThat(actual.get(1).getProductMatrix().getProductName(), is("бананы"));
        MatcherAssert.assertThat(actual2.get(0).getQuantity(), is(42.13));
        MatcherAssert.assertThat(actual2.get(2).getProductMatrix().getProductName(), is("бананы"));
    }

    @Test
    void findAllAndGroupTest() {
        List<WarehouseSumGroup> actual = repository.findAllAndGroup();

        MatcherAssert.assertThat(actual.size(), is(4));
        MatcherAssert.assertThat(actual.get(0).getPrice(), is(80.96));
        MatcherAssert.assertThat(actual.get(1).getPrice(), is(108.43));
    }
}