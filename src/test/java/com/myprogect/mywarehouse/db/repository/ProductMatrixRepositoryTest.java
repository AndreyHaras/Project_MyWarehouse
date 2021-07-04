package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.ProductMatrix;
import org.junit.jupiter.api.Test;
import org.hamcrest.MatcherAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.hamcrest.core.Is.is;

@DataJpaTest
class ProductMatrixRepositoryTest extends ActiveProfile {
    @Autowired private ProductMatrixRepository repository;

    @Test
    void findByIdTest(){
        ProductMatrix actual = repository.findById(1L).get();
        ProductMatrix actual2 = repository.findById(3L).get();

        MatcherAssert.assertThat(actual.getProductName(), is("яблоки"));
        MatcherAssert.assertThat(actual2.getProductName(), is("апельсины"));
    }

    @Test
    void findByProductCodeTest() {
        ProductMatrix actual = repository.findByProductCode(5567322).get();

        MatcherAssert.assertThat(actual.getProductCode(), is(5567322));
    }

    @Test
    void findByProductNameTest() {
        ProductMatrix actual = repository.findByProductName("груши").get();

        MatcherAssert.assertThat(actual.getProductName(), is("груши"));
    }

    @Test
    void findAllRecordsTest(){
        List<ProductMatrix> actual = repository.findAll();

        MatcherAssert.assertThat(actual.size(), is(4));
        MatcherAssert.assertThat(actual.get(0).getProductName(), is("яблоки"));
        MatcherAssert.assertThat(actual.get(2).getProductCode(), is(9990283));
    }
}