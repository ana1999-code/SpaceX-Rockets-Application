package com.example.spacex.rockets.sx.sxdto;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SXRocketDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String _id;

    private int id;

    private boolean active;

    private int stages;

    private int boosters;

    @JsonProperty("cost_per_launch")
    private int costPerLaunch;

    @JsonProperty("success_rate_pct")
    private double successRatePct;

    @JsonProperty("first_flight")
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private Date firstFlight;

    private String country;

    private String company;

    private SXLengthDto height;

    private SXLengthDto diameter;

    @JsonProperty("payload_weights")
    private List<SXPayloadWeightDto> payloadWeights;

    @JsonProperty("first_stage")
    private SXFirstStageDto firstStage;

    @JsonProperty("second_stage")
    private SXSecondStageDto secondStage;

    private SXEnginesDto engines;

    @JsonProperty("landing_legs")
    private SXLandingLegsDto landingLegs;

    private String wikipedia;

    private String description;

    @JsonProperty("rocket_id")
    private String rocketId;

    @JsonProperty("rocket_name")
    private String rocketName;

    @JsonProperty("rocket_type")
    private String rocketType;
}