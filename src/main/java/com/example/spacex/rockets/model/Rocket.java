package com.example.spacex.rockets.model;

import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"_id", "firstFlight"})
@Entity
@Table(name = "rocket",
        uniqueConstraints = @UniqueConstraint(name = "rocket_unique", columnNames = {"id", "rocket_id", "rocket_name"}))
public class Rocket {

    @Id
    @GenericGenerator(name = "uuid-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid-generator")
    private String _id;

    @Column(name = "id",
            nullable = false)
    private Integer id;

    private Boolean active;

    private Integer stages;

    private Integer boosters;

    @Column(name = "cost_per_launch")
    private Integer costPerLaunch;

    @Column(name = "success_rate_pct")
    private Integer successRatePct;

    @Column(name = "first_flight")
    private Date firstFlight;

    private String country;

    private String company;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "meters", column = @Column(name = "height_meters")),
            @AttributeOverride(name = "feet", column = @Column(name = "height_feet"))
    })
    private Length height;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "meters", column = @Column(name = "diameter_meters")),
            @AttributeOverride(name = "feet", column = @Column(name = "diameter_feet"))
    })
    private Length diameter;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "rocket_payload_weight",
            joinColumns = @JoinColumn(name = "rocket_id"),
            inverseJoinColumns = @JoinColumn(name = "payload_id")
    )
    private Set<PayloadWeight> payloadWeights;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "first_stage_id", referencedColumnName = "id")
    private FirstStage firstStage;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "second_stage_id", referencedColumnName = "id")
    private SecondStage secondStage;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "engine_id", referencedColumnName = "id")
    private Engines engines;

    @Embedded
    private LandingLegs landingLegs;

    private String wikipedia;

    @Lob
    private String description;

    @Column(name = "rocket_id",
            nullable = false)
    private String rocketId;

    @Column(name = "rocket_name",
            nullable = false)
    private String rocketName;

    @Column(name = "rocket_type",
            nullable = false)
    private String rocketType;
}