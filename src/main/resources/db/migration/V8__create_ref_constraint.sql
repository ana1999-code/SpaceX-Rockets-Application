-----Add FKs to rocket-----
ALTER TABLE rocket
    ADD CONSTRAINT rocket_first_stage_FK FOREIGN KEY (first_stage_id)
        REFERENCES first_stage (id);

ALTER TABLE rocket
    ADD CONSTRAINT rocket_second_stage_FK FOREIGN KEY (second_stage_id)
        REFERENCES second_stage (id);

ALTER TABLE rocket
    ADD CONSTRAINT rocket_engines_FK FOREIGN KEY (engine_id)
        REFERENCES engines (id);

-----Add FKs to rocket_payload_weight-----
ALTER TABLE rocket_payload_weight
    ADD CONSTRAINT rocket_payload_weight_rocket_FK FOREIGN KEY (rocket_id)
        REFERENCES rocket (_id);

ALTER TABLE rocket_payload_weight
    ADD CONSTRAINT rocket_payload_weight_payload_weight_FK FOREIGN KEY (payload_id)
        REFERENCES payload_weight (_id);

-----Add FK to second_stage-----
ALTER TABLE second_stage
    ADD CONSTRAINT second_stage_payloads_FK FOREIGN KEY (payloads_id)
        REFERENCES payloads (id);
