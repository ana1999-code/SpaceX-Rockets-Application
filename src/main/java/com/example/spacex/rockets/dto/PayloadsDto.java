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
public class PayloadsDto {

    @JsonProperty("option_1")
    private String option1;

    @JsonProperty("option_2")
    private String option2;

    @JsonProperty("composite_fairing")
    private CompositeFairingDto compositeFairing;
}