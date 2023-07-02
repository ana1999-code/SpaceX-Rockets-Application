package com.example.spacex.rockets.integration;

import static com.example.spacex.rockets.utils.TestUtils.TEST_ROCKET;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.spacex.rockets.repository.RocketRepository;
import com.example.spacex.rockets.service.RocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RocketIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RocketRepository rocketRepository;

    @Autowired
    private RocketService rocketService;

    @Autowired
    private ModelMapper modelMapper;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void itShouldReturnRocketsByRocketName() throws URISyntaxException, JsonProcessingException {
        rocketRepository.deleteAll();

        final String rocketId = TEST_ROCKET.getRocketId();
        final String baseUrl = "http://localhost:" + port + "/rockets/" + rocketId;

        URI uri = new URI(baseUrl);

        rocketRepository.save(TEST_ROCKET);

        ResponseEntity<String> actualRocket = restTemplate.getForEntity(uri, String.class);

        assertThat(Objects.requireNonNull(actualRocket.getBody()).contains(objectMapper.writeValueAsString(TEST_ROCKET.getRocketId()))).isTrue();
    }
}
