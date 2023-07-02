package com.example.spacex.rockets.model;

import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "engines")
public class Engines {

    @Id
    @SequenceGenerator(
            name = "engines_id_seq",
            sequenceName = "engines_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "engines_id_seq"
    )
    private Integer id;

    private Integer number;

    private String type;

    private String version;

    private String layout;

    @Column(name = "engine_loss_max")
    private Integer engineLossMax;

    @Column(name = "propellant_1")
    private String propellant1;

    @Column(name = "propellant_2")
    private String propellant2;

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

    @Column(name = "thrust_to_weight")
    private Double thrustToWeight;
}
