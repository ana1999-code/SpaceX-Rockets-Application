package com.example.spacex.rockets.utils;

import java.sql.Date;
import java.util.UUID;

import com.example.spacex.rockets.dto.RocketDto;
import com.example.spacex.rockets.dto.RocketDtoRequest;
import com.example.spacex.rockets.model.Rocket;

public class TestUtils {

    public static final RocketDto TEST_ROCKET_DTO = RocketDto.builder()
            ._id(UUID.randomUUID().toString())
            .id(1)
                .active(true)
                .stages(1)
                .boosters(1)
                .costPerLaunch(10000)
                .successRatePct(5)
                .firstFlight(Date.valueOf("2023-05-27"))
            .country("Moldova")
                .company("SpaceX")
                .wikipedia("https://en.wikipedia.org/wiki/Falcon_1")
                .description("The Falcon 1 was an expendable launch system")
                .rocketId("falcon1")
                .rocketName("Falcon 1")
                .rocketType("rocket")
                .build();

    public static final RocketDtoRequest TEST_ROCKET_DTO_REQUEST = RocketDtoRequest.builder()
            .id(1)
                .active(true)
                .stages(1)
                .boosters(1)
                .costPerLaunch(10000)
                .successRatePct(5)
                .firstFlight(Date.valueOf("2023-05-27"))
            .country("Moldova")
                .company("SpaceX")
                .wikipedia("https://en.wikipedia.org/wiki/Falcon_1")
                .description("The Falcon 1 was an expendable launch system")
                .rocketId("falcon1")
                .rocketName("Falcon 1")
                .rocketType("rocket")
                .build();

    public static final Rocket TEST_ROCKET = Rocket.builder()
            ._id(UUID.randomUUID().toString())
            .id(1)
                .active(true)
                .stages(1)
                .boosters(1)
                .costPerLaunch(10000)
                .successRatePct(5)
                .firstFlight(Date.valueOf("2023-05-27"))
            .country("Moldova")
                .company("SpaceX")
                .wikipedia("https://en.wikipedia.org/wiki/Falcon_1")
                .description("The Falcon 1 was an expendable launch system")
                .rocketId("falcon1")
                .rocketName("Falcon 1")
                .rocketType("rocket")
                .build();
}
