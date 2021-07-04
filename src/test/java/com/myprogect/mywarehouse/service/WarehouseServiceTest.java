package com.myprogect.mywarehouse.service;

import com.myprogect.mywarehouse.db.entity.ConsignmentNote;
import com.myprogect.mywarehouse.db.entity.ProductMatrix;
import com.myprogect.mywarehouse.db.entity.Warehouse;
import com.myprogect.mywarehouse.db.repository.ProductMatrixRepository;
import com.myprogect.mywarehouse.db.repository.WarehouseRepository;
import com.myprogect.mywarehouse.service.dto.WarehouseDTO;
import com.myprogect.mywarehouse.service.search.WarehouseSumGroup;
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
class WarehouseServiceTest {
    @MockBean
    WarehouseRepository repository;
    @MockBean
    ProductMatrixRepository productMatrixRepository;
    @Autowired
    WarehouseService service;

    @Test
    void byIdTest() {
        when(repository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(
                        new Warehouse().setId(1L).setConsignmentId(1875L).setPrice(124.56).setQuantity(80.96)
                                .setConsignmentCode(new ConsignmentNote().setId(1L).setConsignmentNoteDate(Date.valueOf("2021-02-27")).setConsignmentNoteId(889975L))
                                .setProductMatrix(new ProductMatrix().setId(1L).setProductCode(889456).setProductName("Product"))));
        WarehouseDTO fromService = service.byId(1L);

        MatcherAssert.assertThat(fromService.getPrice(), is("124.56"));
        MatcherAssert.assertThat(fromService.getConsignmentCode().getConsignmentNoteDate(), is("2021-02-27"));
        MatcherAssert.assertThat(fromService.getProductMatrix().getProductName(), is("Product"));
    }

    @Test
    void byConsignmentNoteIdTest() {
        when(repository.findByConsignmentId(1342532L)).thenReturn(new ArrayList<Warehouse>(){{
            add(new Warehouse().setId(1L).setConsignmentId(1875L).setPrice(124.56).setQuantity(80.96)
                    .setConsignmentCode(new ConsignmentNote().setId(1L).setConsignmentNoteDate(Date.valueOf("2021-02-27")).setConsignmentNoteId(1342532L))
                    .setProductMatrix(new ProductMatrix().setId(1L).setProductCode(889456).setProductName("Product")));
            add(new Warehouse().setId(2L).setConsignmentId(1875L).setPrice(100.56).setQuantity(20.96)
                    .setConsignmentCode(new ConsignmentNote().setId(2L).setConsignmentNoteDate(Date.valueOf("2021-03-17")).setConsignmentNoteId(1342532L))
                    .setProductMatrix(new ProductMatrix().setId(2L).setProductCode(889456).setProductName("Product2")));
        }});

        List<WarehouseDTO> fromService = service.byConsignmentNoteId(1342532L);

        MatcherAssert.assertThat(fromService.get(0).getQuantity(), is("80.96"));
        MatcherAssert.assertThat(fromService.size(), is(2));
        MatcherAssert.assertThat(fromService.get(1).getConsignmentCode().getConsignmentNoteId(), is("1342532"));
    }

    @Test
    void saveDataTest() {
        doThrow(new RuntimeException()).when(repository)
                .save(new Warehouse().setQuantity(200.96).setPrice(40.96).setProduct_id(2L).setConsignmentId(1L));
        try {
            service.saveData(new WarehouseDTO().setPrice("40.96").setQuantity("200.96").setProductId(2L).setConsignmentId(1L));
            fail();
        }catch (Exception ex){

        }
    }

    @Test
    void getAllRecordTest() {
        when(repository.findAll()).thenReturn(new ArrayList<Warehouse>(){{
            add(new Warehouse().setId(1L).setConsignmentId(1875L).setPrice(124.56).setQuantity(80.96)
                    .setConsignmentCode(new ConsignmentNote().setId(1L).setConsignmentNoteDate(Date.valueOf("2021-02-27")).setConsignmentNoteId(1342532L))
                    .setProductMatrix(new ProductMatrix().setId(1L).setProductCode(889456).setProductName("Product")));
            add(new Warehouse().setId(2L).setConsignmentId(1875L).setPrice(100.56).setQuantity(20.96)
                    .setConsignmentCode(new ConsignmentNote().setId(2L).setConsignmentNoteDate(Date.valueOf("2021-03-17")).setConsignmentNoteId(1342532L))
                    .setProductMatrix(new ProductMatrix().setId(2L).setProductCode(889456).setProductName("Product2")));
        }});

        List<WarehouseDTO> fromService = service.getAllRecord();

        MatcherAssert.assertThat(fromService.size(), is(2));
        MatcherAssert.assertThat(fromService.get(0).getProductMatrix().getProductName(), is("Product"));
        MatcherAssert.assertThat(fromService.get(1).getConsignmentCode().getConsignmentNoteDate(), is("2021-03-17"));
    }

    @Test
    void findTotalSumQuantityTest() {
        when(productMatrixRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(new ProductMatrix().setId(1L).setProductName("Product").setProductCode(11441122)));
        when(repository.findAllAndGroup()).thenReturn(new ArrayList<WarehouseSumGroup>(){{
            add(new WarehouseSumGroup() {
                @Override
                public Double getPrice() {
                    return Double.valueOf("50.86");
                }

                @Override
                public Double getQuantity() {
                    return Double.valueOf("100.86");
                }

                @Override
                public Long getProductId() {
                    return Long.valueOf("1");
                }
            });
        }});

        List<WarehouseDTO> fromService = service.findTotalSumQuantity();

        MatcherAssert.assertThat(fromService.size(), is(1));
        MatcherAssert.assertThat(fromService.get(0).getQuantity(), is("100.86"));
        MatcherAssert.assertThat(fromService.get(0).getProductMatrix().getProductName(), is("Product"));
    }
}