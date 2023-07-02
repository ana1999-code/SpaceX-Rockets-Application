package com.example.spacex.rockets.controller;

import java.util.Date;

import com.example.spacex.rockets.assembler.RocketResourceAssembler;
import com.example.spacex.rockets.service.RocketService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spacex.rockets.dto.RocketDto;
import com.example.spacex.rockets.dto.RocketDtoRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Profile("rocket-app")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rockets")
public class RocketController {

    private final RocketService rocketService;

    private final RocketResourceAssembler rocketResourceAssembler;

    private final PagedResourcesAssembler pagedResourcesAssembler;

    @GetMapping
    public PagedModel<RocketDto> getAllRockets(final @PageableDefault Pageable pageable,
                                               final @RequestParam(required = false) Integer lowerCostPerLaunch,
                                               final @RequestParam(required = false) Integer upperCostPerLaunch,
                                               final @RequestParam(required = false) Double heightMeters,
                                               final @RequestParam(required = false) Double diameterMeters,
                                               final @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        final Page<RocketDto> allRockets = rocketService.getAllRockets(pageable,
                lowerCostPerLaunch,
                upperCostPerLaunch,
                heightMeters,
                diameterMeters,
                date);
        return pagedResourcesAssembler.toModel(allRockets, rocketResourceAssembler);
    }

    @GetMapping("{rocket_id}")
    public EntityModel<RocketDto> getRocketByRocketId(final @PathVariable("rocket_id") String rocketId) {
        final RocketDto rocketByRocketId = rocketService.getRocketByRocketId(rocketId);
        return rocketResourceAssembler.toModel(rocketByRocketId);
    }

    @PostMapping
    public ResponseEntity<RocketDto> createRocket(final @RequestBody @Valid RocketDtoRequest request) {
        rocketService.createRocket(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("{rocket_id}")
    public ResponseEntity<RocketDto> deleteRocketByRocketId(final @PathVariable("rocket_id") String rocketId) {
        rocketService.deleteRocketByRocketId(rocketId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
