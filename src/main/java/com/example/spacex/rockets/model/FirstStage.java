package com.example.spacex.rockets.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "first_stage")
public class FirstStage extends Stage {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "kN", column = @Column(name = "sea_level_kN")),
            @AttributeOverride(name = "lbf", column = @Column(name = "sea_level_lbf"))
    })
    private Thrust thrustSeaLevel;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "kN", column = @Column(name = "vacuum_level_kN")),
            @AttributeOverride(name = "lbf", column = @Column(name = "vacuum_level_lbf"))
    })
    private Thrust thrustVacuum;
}
