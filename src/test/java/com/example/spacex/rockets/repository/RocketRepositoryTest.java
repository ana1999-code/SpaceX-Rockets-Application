package com.example.spacex.rockets.repository;

import com.example.spacex.rockets.model.Rocket;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class RocketRepositoryTest {

    private static final String DATA_SET = "/dataset/dataSet.xml";

    private static final String ROCKET_ID = "falcon1";

    private static final String ROCKET_NAME = "Falcon 1";

    private static final int ID = 1;

    private static final String UNKNOWN = "unknown";

    @Autowired
    private RocketRepository rocketRepository;

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
            value = DATA_SET)
    public void itShouldReturnRocket_WhenGetRocketById() {
        Optional<Rocket> rocket = rocketRepository.findByRocketId(ROCKET_ID);

        assertThat(rocket.get().getRocketId()).isEqualTo(ROCKET_ID);
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
            value = DATA_SET)
    void itShouldReturnEmpty_WhenGetRocketByUnknownRocketId() {
        Optional<Rocket> rocket = rocketRepository.findByRocketId(UNKNOWN);

        assertThat(rocket.isEmpty()).isTrue();
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
            value = DATA_SET)
    void itShouldReturnTrue_WhenCheckIfExistByRocketId() {
        boolean existsByRocketId = rocketRepository.existsByRocketId(ROCKET_ID);

        assertThat(existsByRocketId).isTrue();
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
            value = DATA_SET)
    void itShouldReturnFalse_WhenCheckIfExistByUnknownRocketId() {
        boolean existsByRocketId = rocketRepository.existsByRocketId(UNKNOWN);

        assertThat(existsByRocketId).isFalse();
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
            value = DATA_SET)
    void itShouldReturnTrue_WhenCheckIfExistById() {
        boolean existsById = rocketRepository.existsById(ID);

        assertThat(existsById).isTrue();
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
            value = DATA_SET)
    void itShouldReturnFalse_WhenCheckIfExistByUnknownId() {
        boolean existsById = rocketRepository.existsById(0);

        assertThat(existsById).isFalse();
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
            value = DATA_SET)
    void itShouldReturnTrue_WhenCheckIfExistByRocketName() {
        boolean existsByRocketName = rocketRepository.existsByRocketName(ROCKET_NAME);

        assertThat(existsByRocketName).isTrue();
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
            value = DATA_SET)
    void itShouldReturnFalse_WhenCheckIfExistByUnknownName() {
        boolean existsByRocketName = rocketRepository.existsByRocketName("Unknown");

        assertThat(existsByRocketName).isFalse();
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
            value = DATA_SET)
    void itShouldDeleteRocketByRocketId() {
        rocketRepository.deleteByRocketId(ROCKET_ID);

        boolean existsByRocketId = rocketRepository.existsByRocketId(ROCKET_ID);
        List<Rocket> rockets = rocketRepository.findAll();
        Optional<Rocket> rocket = rocketRepository.findByRocketId(ROCKET_ID);

        assertThat(existsByRocketId).isFalse();
        assertThat(rockets.size()).isEqualTo(3);
        assertThat(rocket.isEmpty()).isTrue();
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT,
            value = DATA_SET)
    void itShouldNotDeleteRocket_WhenDeletingByUnknownRocketId() {
        rocketRepository.deleteByRocketId(UNKNOWN);

        List<Rocket> rockets = rocketRepository.findAll();

        assertThat(rockets.size()).isEqualTo(4);
    }
}