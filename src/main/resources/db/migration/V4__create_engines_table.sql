CREATE SEQUENCE engines_id_seq start with 1;

CREATE TABLE engines
(
    id INT,
    number INT,
    type VARCHAR,
    version VARCHAR,
    layout VARCHAR,
    engine_loss_max INT,
    propellant_1 VARCHAR,
    propellant_2 VARCHAR,
    sea_level_kN INT,
    sea_level_lbf INT,
    vacuum_level_kN INT,
    vacuum_level_lbf INT,
    thrust_to_weight DOUBLE PRECISION,
    CONSTRAINT engines_id_pk PRIMARY KEY (id)
);