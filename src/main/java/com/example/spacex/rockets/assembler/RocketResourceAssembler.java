package com.example.spacex.rockets.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.spacex.rockets.controller.RocketController;
import com.example.spacex.rockets.dto.RocketDto;

@Component
public class RocketResourceAssembler implements RepresentationModelAssembler<RocketDto, EntityModel<RocketDto>> {
    @Override
    public EntityModel<RocketDto> toModel(RocketDto rocketDto) {
        return EntityModel.of(rocketDto,
                linkTo(methodOn(RocketController.class)
                        .getAllRockets(
                                null,
                                null,
                                null,
                                null,
                                null,
                                null))
                        .withRel("all_rockets"),
                linkTo(methodOn(RocketController.class)
                        .getRocketByRocketId(rocketDto.getRocketId()))
                        .withSelfRel());
    }
}
