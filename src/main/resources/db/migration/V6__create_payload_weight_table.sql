CREATE SEQUENCE payload_weight_id_seq start with 1;

CREATE TABLE payload_weight
(
    _id INT,
    id VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    kg INT,
    lb INT,
    CONSTRAINT payload_weight_id_pk PRIMARY KEY (_id)
);