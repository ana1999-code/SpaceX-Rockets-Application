CREATE SEQUENCE payloads_id_seq start with 1;

CREATE TABLE payloads
(
    id INT,
    option_1 VARCHAR,
    option_2 VARCHAR,
    height_meters DOUBLE PRECISION,
    height_feet DOUBLE PRECISION,
    diameter_meters DOUBLE PRECISION,
    diameter_feet DOUBLE PRECISION,
    CONSTRAINT payloads_id_pk PRIMARY KEY (id)
);