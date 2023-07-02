package com.example.spacex.rockets.dto;

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
public class SecondStageDto {

    private boolean reusable;

    private int engines;

    @JsonProperty("fuel_amount_tones")
    private int fuelAmountTones;

    @JsonProperty("burn_time_sec")
    private int burnTimeSec;

    private ThrustDto thrust;

    private PayloadsDto payloads;
}