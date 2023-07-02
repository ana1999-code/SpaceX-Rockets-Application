CREATE TABLE second_stage
(
    id INT,
    reusable BOOLEAN,
    engines INT,
    fuel_amount_tones INT,
    burn_time_sec INT,
    kN INT,
    lbf INT,
    payloads_id INT,
    CONSTRAINT second_stage_id_pk PRIMARY KEY (id)
);