package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.ProductMatrix;
import com.myprogect.mywarehouse.db.repository.ProductMatrixRepository;
import com.myprogect.mywarehouse.service.dto.ProductMatrixDTO;
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
class ProductMatrixServiceTest {
    @MockBean
    ProductMatrixRepository repository;
    @Autowired
    ProductMatrixService service;

    @Test
    void byIdTest() {
        when(repository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(new ProductMatrix().setId(1L).setProductName("Product").setProductCode(11441122)));

        ProductMatrixDTO fromService = service.byId(1L);

        MatcherAssert.assertThat(fromService.getProductName(), is("Product"));
    }

    @Test
    void saveProductTest() {
        doThrow(new RuntimeException()).when(repository)
                .save(new ProductMatrix().setProductCode(8899).setProductName("SomeProduct"));
        try{
            service.saveProduct(new ProductMatrixDTO().setProductCode(8899).setProductName("SomeProduct"));
            fail();
        }catch (Exception ex){}
    }

    @Test
    void findByProductCodeTest() {
        when(repository.findByProductCode(8899))
                .thenReturn(java.util.Optional.ofNullable((new ProductMatrix().setProductCode(9008))));

        ProductMatrixDTO fromService = service.findByProductCode(8899);

        MatcherAssert.assertThat(fromService.getProductCode(), is(9008));
    }

    @Test
    void findByProductNameTest() {
        when(repository.findByProductName("Product"))
                .thenReturn(java.util.Optional.ofNullable((new ProductMatrix().setProductName("SomeProduct"))));
        ProductMatrixDTO fromService = service.findByProductName("Product");

        MatcherAssert.assertThat(fromService.getProductName(), is("SomeProduct"));
    }

    @Test
    void findAllRecordTest() {
        when(repository.findAll())
                .thenReturn(new ArrayList<ProductMatrix>(){{
                    add(new ProductMatrix().setId(1L).setProductCode(8899).setProductName("Product1"));
                    add(new ProductMatrix().setId(2L).setProductCode(778866).setProductName("Product2"));}});
        List<ProductMatrixDTO> fromService = service.findAllRecord();

        MatcherAssert.assertThat(fromService.size(), is(2));
        MatcherAssert.assertThat(fromService.get(0).getProductName(), is("Product1"));
        MatcherAssert.assertThat(fromService.get(1).getProductCode(), is(778866));

    }
}