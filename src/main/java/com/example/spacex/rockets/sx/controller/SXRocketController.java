package com.example.spacex.rockets.sx.controller;

import java.util.List;

import com.example.spacex.rockets.dto.RocketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("${application.path}")
@ConditionalOnBean(WebClient.class)
public class SXRocketController {

    @Autowired
    private com.example.spacex.rockets.sx.service.SXRocketService SXRocketService;

    @GetMapping("/{rocket_id}")
    public RocketDto getRocketById(
            @PathVariable("rocket_id") String rocketId,
            @RequestParam(value = "id", required = false) Boolean id
    ) {
        return SXRocketService.getRocketById(rocketId, id);
    }

    @GetMapping
    public List<RocketDto> getRockets(
            @RequestParam(name = "id", required = false) Boolean id,
            @RequestParam(name = "limit", required = false) Integer limit,
            @RequestParam(name = "offset", required = false) Integer offset,
            @RequestParam(name = "saveToDatabase", required = false, defaultValue = "false") Boolean saveToDatabase
    ) {
        return SXRocketService.getRockets(id, limit, offset, saveToDatabase);
    }
}
