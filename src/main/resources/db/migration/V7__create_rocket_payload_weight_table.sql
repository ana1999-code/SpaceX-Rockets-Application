CREATE TABLE rocket_payload_weight
(
    rocket_id VARCHAR NOT NULL,
    payload_id INT NOT NULL,
    CONSTRAINT ROCKET_payload_weight_id_pk PRIMARY KEY (rocket_id, payload_id)
);