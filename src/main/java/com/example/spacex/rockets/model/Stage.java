package com.example.spacex.rockets.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@MappedSuperclass
public abstract class Stage {

    @Id
    @SequenceGenerator(name = "stage_id_seq",
            sequenceName = "stage_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "stage_id_seq")
    private Integer id;

    private Boolean reusable;

    private Integer engines;

    @Column(name = "fuel_amount_tones")
    private Integer fuelAmountTones;

    @Column(name = "burn_time_sec")
    private Integer burnTimeSec;
}
