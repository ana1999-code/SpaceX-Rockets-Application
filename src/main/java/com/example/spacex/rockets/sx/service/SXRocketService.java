package com.example.spacex.rockets.sx.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.spacex.rockets.dto.RocketDto;
import com.example.spacex.rockets.exception.ResourceNotFoundException;
import com.example.spacex.rockets.model.Rocket;
import com.example.spacex.rockets.repository.RocketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.spacex.rockets.sx.sxdto.SXRocketDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@ConditionalOnBean(WebClient.class)
public class SXRocketService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RocketRepository rocketRepository;

    public RocketDto getRocketById(String rocketId, Boolean id) {
        final String uri = "/rockets/{rocket_id}";

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(uri)
                .queryParamIfPresent("id", Optional.ofNullable(id));

        return webClient.get()
                .uri(String.valueOf(uriComponentsBuilder.build()), rocketId)
                .retrieve()
                .bodyToMono(SXRocketDto.class)
                .onErrorResume(error -> {
                    if (error.getMessage().contains("Not Found")) {
                        return Mono.error(new ResourceNotFoundException(
                                "Rocket with id = [%s] not found".formatted(rocketId)));
                    }
                    return Mono.error(new Throwable());
                })
                .map(sxRocketDto -> mapper.map(sxRocketDto, RocketDto.class))
                .block();
    }

    public List<RocketDto> getRockets(Boolean id, Integer limit, Integer offset, Boolean saveToDatabase) {
        final List<RocketDto> rocketDtoList = getRocketDtos(id, limit, offset);

        if (saveToDatabase.booleanValue()) {
            save(Objects.requireNonNull(rocketDtoList));
        }

        return rocketDtoList;
    }

    private List<RocketDto> getRocketDtos(Boolean id, Integer limit, Integer offset) {
        final String uri = "/rockets";

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(uri)
                .queryParamIfPresent("id", Optional.ofNullable(id))
                .queryParamIfPresent("limit", Optional.ofNullable(limit))
                .queryParamIfPresent("offset", Optional.ofNullable(offset));

        return webClient.get()
                .uri(String.valueOf(uriComponentsBuilder.build()))
                .retrieve()
                .bodyToFlux(SXRocketDto.class)
                .map(sxRocketDto -> mapper.map(sxRocketDto, RocketDto.class))
                .collectList()
                .block();
    }

    private void save(List<RocketDto> rocketDtoList) {
        final List<Rocket> rockets = rocketDtoList.stream()
                .map(rocketDto -> mapper.map(rocketDto, Rocket.class))
                .toList();
        rocketRepository.saveAll(rockets);
    }
}
