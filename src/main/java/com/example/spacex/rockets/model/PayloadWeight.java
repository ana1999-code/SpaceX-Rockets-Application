package com.example.spacex.rockets.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@EqualsAndHashCode(exclude = "_id")
@Entity
@Table(name = "payload_weight")
public class PayloadWeight {

    @Id
    @SequenceGenerator(name = "payload_weight_id_seq",
            sequenceName = "payload_weight_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "payload_weight_id_seq")
    private Integer _id;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    private Integer kg;

    private Integer lb;

}