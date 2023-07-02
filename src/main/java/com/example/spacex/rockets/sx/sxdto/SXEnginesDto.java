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
public class SXEnginesDto {

    private int number;

    private String type;

    private String version;

    private String layout;

    @JsonProperty("engine_loss_max")
    private int engineLossMax;

    @JsonProperty("propellant_1")
    private String propellant1;

    @JsonProperty("propellant_2")
    private String propellant2;

    @JsonProperty("thrust_sea_level")
    private SXThrustDto thrustSeaLevel;

    @JsonProperty("thrust_vacuum")
    private SXThrustDto thrustVacuum;

    @JsonProperty("thrust_to_weight")
    private double thrustToWeight;
}
