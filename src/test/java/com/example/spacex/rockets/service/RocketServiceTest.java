package com.example.spacex.rockets.service;

import com.example.spacex.rockets.dto.RocketDto;
import com.example.spacex.rockets.exception.DuplicateResourceException;
import com.example.spacex.rockets.exception.ResourceNotFoundException;
import com.example.spacex.rockets.model.Rocket;
import com.example.spacex.rockets.repository.RocketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static com.example.spacex.rockets.utils.TestUtils.TEST_ROCKET;
import static com.example.spacex.rockets.utils.TestUtils.TEST_ROCKET_DTO;
import static com.example.spacex.rockets.utils.TestUtils.TEST_ROCKET_DTO_REQUEST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RocketServiceTest {

    @Mock
    private RocketRepository rocketRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private RocketService rocketService;

    @Captor
    private ArgumentCaptor<Rocket> rocketArgumentCaptor;

    @Test
    void itShouldReturnAllRockets_WhenGetAllRockets() {
        final Page<Rocket> rockets = new PageImpl<>(List.of(TEST_ROCKET));
        final Page<RocketDto> expectedRocketDtos = new PageImpl<>(List.of(TEST_ROCKET_DTO));
        final Pageable pageable = PageRequest.of(0, 1);

        when(mapper.map(TEST_ROCKET, RocketDto.class)).thenReturn(TEST_ROCKET_DTO);
        when(rocketRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(rockets);

        Page<RocketDto> actualRocketDtos = rocketService.getAllRockets(pageable,
                null,
                null,
                null,
                null,
                null);

        verify(mapper).map(TEST_ROCKET, RocketDto.class);

        assertThat(actualRocketDtos.getContent()).isEqualTo(expectedRocketDtos.getContent());
    }

    @Test
    void itShouldReturnRocket_WhenGetRocketByRocketId() {
        final String rocketId = TEST_ROCKET.getRocketId();

        when(mapper.map(TEST_ROCKET, RocketDto.class)).thenReturn(TEST_ROCKET_DTO);
        when(rocketRepository.findByRocketId(rocketId)).thenReturn(Optional.of(TEST_ROCKET));

        RocketDto actualRocket = rocketService.getRocketByRocketId(rocketId);

        assertThat(actualRocket).isEqualTo(TEST_ROCKET_DTO);

        verify(mapper).map(TEST_ROCKET, RocketDto.class);
        verify(rocketRepository).findByRocketId(rocketId);
    }

    @Test
    void itShouldThrowResourceNotFound_WhenTryingToGetRocketWithInvalidId() {
        final String rocketId = "Invalid";
        String errorMessage = "Rocket with id = [%s] does not exist".formatted(rocketId);

        when(rocketRepository.findByRocketId(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rocketService.getRocketByRocketId(rocketId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(errorMessage);

        verify(rocketRepository).findByRocketId(rocketId);
    }

    @Test
    void itShouldCreateNewRocket() {
        when(mapper.map(TEST_ROCKET_DTO_REQUEST, Rocket.class)).thenReturn(TEST_ROCKET);
        when(rocketRepository.save(TEST_ROCKET)).thenReturn(TEST_ROCKET);

        rocketService.createRocket(TEST_ROCKET_DTO_REQUEST);

        verify(rocketRepository).existsById(TEST_ROCKET_DTO_REQUEST.getId());
        verify(rocketRepository).existsByRocketName(TEST_ROCKET_DTO_REQUEST.getRocketName());
        verify(rocketRepository).existsByRocketId(TEST_ROCKET_DTO_REQUEST.getRocketId());
        verify(mapper).map(TEST_ROCKET_DTO_REQUEST, Rocket.class);

        verify(rocketRepository).save(rocketArgumentCaptor.capture());
        assertThat(rocketArgumentCaptor.getValue()).isEqualTo(TEST_ROCKET);
    }

    @Test
    void itShouldThrow_WhenCreateRocketWithExistingId() {
        String errorMessage = "Rocket with id = [%s] already exists".formatted(TEST_ROCKET_DTO_REQUEST.getId());

        when(rocketRepository.existsById(anyInt())).thenReturn(true);

        assertThatThrownBy(() -> rocketService.createRocket(TEST_ROCKET_DTO_REQUEST))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining(errorMessage);

        verify(rocketRepository).existsById(anyInt());
        verify(rocketRepository, never()).existsByRocketName(anyString());
        verify(rocketRepository, never()).existsByRocketId(anyString());

    }

    @Test
    void itShouldThrow_WhenCreateRocketWithExistingRocketId() {
        String errorMessage = "Rocket with rocket id = [%s] already exists".formatted(TEST_ROCKET_DTO_REQUEST.getRocketId());

        when(rocketRepository.existsByRocketId(anyString())).thenReturn(true);

        assertThatThrownBy(() -> rocketService.createRocket(TEST_ROCKET_DTO_REQUEST))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining(errorMessage);

        verify(rocketRepository).existsById(anyInt());
        verify(rocketRepository).existsByRocketId(anyString());
        verify(rocketRepository, never()).existsByRocketName(anyString());
    }

    @Test
    void itShouldThrow_WhenCreateRocketWithExistingRocketName() {
        String errorMessage = "Rocket with name = [%s] already exists".formatted(TEST_ROCKET_DTO_REQUEST.getRocketName());

        when(rocketRepository.existsByRocketName(anyString())).thenReturn(true);

        assertThatThrownBy(() -> rocketService.createRocket(TEST_ROCKET_DTO_REQUEST))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining(errorMessage);

        verify(rocketRepository).existsById(anyInt());
        verify(rocketRepository).existsByRocketId(anyString());
        verify(rocketRepository).existsByRocketName(anyString());
    }

    @Test
    void itShouldDeleteRocketByRocketId() {
        when(rocketRepository.existsByRocketId(TEST_ROCKET.getRocketId())).thenReturn(true);

        assertThatNoException().isThrownBy(() -> rocketService.deleteRocketByRocketId(TEST_ROCKET.getRocketId()));

        verify(rocketRepository).existsByRocketId(TEST_ROCKET.getRocketId());
    }

    @Test
    void itShouldThrow_WhenDeletingRocketWithUnknownRocketId() {
        String errorMessage = "Rocket with id = [%s] not found".formatted(TEST_ROCKET.getRocketId());

        when(rocketRepository.existsByRocketId(TEST_ROCKET.getRocketId())).thenReturn(false);

        assertThatThrownBy(() -> rocketService.deleteRocketByRocketId(TEST_ROCKET.getRocketId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(errorMessage);
    }
}