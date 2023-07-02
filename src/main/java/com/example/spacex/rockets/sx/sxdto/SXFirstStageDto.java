package com.example.spacex.rockets.sx.sxdto;

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
public class SXFirstStageDto {

    private boolean reusable;

    private int engines;

    @JsonProperty("fuel_amount_tones")
    private int fuelAmountTons;

    @JsonProperty("burn_time_sec")
    private int burnTimeSec;

    @JsonProperty("thrust_sea_level")
    private SXThrustDto thrustSeaLevel;

    @JsonProperty("thrust_vacuum")
    private SXThrustDto thrustVacuum;
}
