package com.myprogect.mywarehouse.db.repository;

import com.myprogect.mywarehouse.db.entity.Liability;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.core.Is.is;

@DataJpaTest
public class LiabilityRepositoryTest extends ActiveProfile {
    @Autowired private LiabilityRepository repository;

    @Test
    void findByIdTest(){
        Optional<Liability> actual = repository.findById(2L);

        MatcherAssert.assertThat(actual.get().getLiabilityCode(), is(1));
    }

    @Test
    void findAllRecordsTest(){
        List<Liability> actual = repository.findAll();

        MatcherAssert.assertThat(actual.size(), is(3));
        MatcherAssert.assertThat(actual.get(2).getLiabilityCode(), is(2));
    }
}
