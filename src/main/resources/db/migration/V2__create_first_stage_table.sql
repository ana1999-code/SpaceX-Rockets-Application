CREATE SEQUENCE stage_id_seq;

CREATE TABLE first_stage
(
    id INT,
    reusable BOOLEAN,
    engines INT,
    fuel_amount_tones INT,
    burn_time_sec INT,
    sea_level_kN INT,
    sea_level_lbf INT,
    vacuum_level_kN INT,
    vacuum_level_lbf INT,
    CONSTRAINT first_stage_id_pk PRIMARY KEY (id)
);