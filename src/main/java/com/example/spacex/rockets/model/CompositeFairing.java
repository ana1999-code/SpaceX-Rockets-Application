package com.example.spacex.rockets.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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
@Embeddable
public class CompositeFairing {

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
}
