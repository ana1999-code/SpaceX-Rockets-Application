package com.example.spacex.rockets.service;

import java.util.Date;
import java.util.List;

import com.example.spacex.rockets.repository.RocketRepository;
import com.example.spacex.rockets.repository.specification.RocketSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spacex.rockets.dto.RocketDto;
import com.example.spacex.rockets.dto.RocketDtoRequest;
import com.example.spacex.rockets.exception.DuplicateResourceException;
import com.example.spacex.rockets.exception.ResourceNotFoundException;
import com.example.spacex.rockets.model.Rocket;

import lombok.RequiredArgsConstructor;

@Profile("rocket-app")
@Service
@RequiredArgsConstructor
public class RocketService {

    private final RocketRepository rocketRepository;

    private final ModelMapper mapper;

    @Transactional
    @Cacheable("rocketsCache")
    public Page<RocketDto> getAllRockets(Pageable pageable,
                                         Integer lowerBound,
                                         Integer upperBound,
                                         Double heightMeters,
                                         Double diameterMeters,
                                         Date date) {

        final Specification<Rocket> specification = Specification
                .where(RocketSpecification.between("costPerLaunch", lowerBound, upperBound))
                .and(RocketSpecification.equalTo("firstFlight", date))
                .and(RocketSpecification.equalToJoinedTableField("height", "meters", heightMeters))
                .and(RocketSpecification.equalToJoinedTableField("diameter", "meters", diameterMeters));

        final Page<Rocket> rockets = rocketRepository.findAll(specification, pageable);

        final List<RocketDto> rocketDtos = rockets.stream()
                .map(rocket -> mapper.map(rocket, RocketDto.class))
                .toList();

        return new PageImpl<>(rocketDtos, pageable, pageable.getPageSize());
    }

    @Transactional
    @Cacheable("rocketsCache")
    public RocketDto getRocketByRocketId(String rocketId) {

        final Rocket rocket = rocketRepository.findByRocketId(rocketId).orElseThrow(
                () -> new ResourceNotFoundException("Rocket with id = [%s] does not exist".formatted(rocketId))
        );

        return mapper.map(rocket, RocketDto.class);
    }

    @Transactional
    @CacheEvict(value = "rocketsCache", allEntries = true)
    public void createRocket(RocketDtoRequest request) {
        validateForExistingFields(request);

        final Rocket rocket = mapper.map(request, Rocket.class);

        rocketRepository.save(rocket);
    }

    @Transactional
    @CacheEvict(value = "rocketsCache", allEntries = true)
    public void deleteRocketByRocketId(String rocketId) {
        if (!rocketRepository.existsByRocketId(rocketId)) {
            throw new ResourceNotFoundException("Rocket with id = [%s] not found".formatted(rocketId));
        }

        rocketRepository.deleteByRocketId(rocketId);
    }

    private void validateForExistingFields(RocketDtoRequest request) {
        if (rocketRepository.existsById(request.getId())) {
            throw new DuplicateResourceException("Rocket with id = [%s] already exists".formatted(request.getId()));
        }

        if (rocketRepository.existsByRocketId(request.getRocketId())) {
            throw new DuplicateResourceException("Rocket with rocket id = [%s] already exists".formatted(request.getRocketId()));
        }

        if (rocketRepository.existsByRocketName(request.getRocketName())) {
            throw new DuplicateResourceException("Rocket with name = [%s] already exists".formatted(request.getRocketName()));
        }
    }
}
