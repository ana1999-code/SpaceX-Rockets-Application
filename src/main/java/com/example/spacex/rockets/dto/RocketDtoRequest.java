package com.example.spacex.rockets.dto;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class RocketDtoRequest {

    @NotNull
    @Positive
    private final int id;

    private final boolean active;

    private final int stages;

    private final int boosters;

    @JsonProperty("cost_per_launch")
    private final int costPerLaunch;

    @JsonProperty("success_rate_pct")
    private final double successRatePct;

    @JsonProperty("first_flight")
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent
    private final Date firstFlight;

    private final String country;

    private final String company;

    private final LengthDto height;

    private final LengthDto diameter;

    @JsonProperty("payload_weights")
    private final List<PayloadWeightDto> payloadWeights;

    @JsonProperty("first_stage")
    private final FirstStageDto firstStage;

    @JsonProperty("second_stage")
    private final SecondStageDto secondStage;

    private final EnginesDto engines;

    @JsonProperty("landing_legs")
    private final LandingLegsDto landingLegs;

    private final String wikipedia;

    private final String description;

    @JsonProperty("rocket_id")
    @NotBlank
    private final String rocketId;

    @JsonProperty("rocket_name")
    @NotBlank
    private final String rocketName;

    @JsonProperty("rocket_type")
    @NotBlank
    private final String rocketType;
}
