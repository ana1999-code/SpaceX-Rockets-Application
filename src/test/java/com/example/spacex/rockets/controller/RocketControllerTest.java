package com.example.spacex.rockets.controller;

import static com.example.spacex.rockets.utils.TestUtils.TEST_ROCKET_DTO;
import static com.example.spacex.rockets.utils.TestUtils.TEST_ROCKET_DTO_REQUEST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spacex.rockets.assembler.RocketResourceAssembler;
import com.example.spacex.rockets.dto.RocketDto;
import com.example.spacex.rockets.dto.RocketDtoRequest;
import com.example.spacex.rockets.exception.DuplicateResourceException;
import com.example.spacex.rockets.exception.ResourceNotFoundException;
import com.example.spacex.rockets.service.RocketService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest
public class RocketControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${test.uriTemplate}")
    private String uriTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RocketService rocketService;

    @MockBean
    private RocketResourceAssembler rocketResourceAssembler;

    @Test
    void itShouldReturnAllRockets_WhenGetAllRockets() throws Exception {
        final List<RocketDto> rocketDtoList = List.of(TEST_ROCKET_DTO);
        final Page<RocketDto> page = new PageImpl<>(rocketDtoList, Pageable.unpaged(), rocketDtoList.size());

        when(rocketService.getAllRockets(any(Pageable.class), any(), any(), any(), any(), any())).thenReturn(page);
        when(rocketResourceAssembler.toModel(TEST_ROCKET_DTO)).thenReturn(EntityModel.of(TEST_ROCKET_DTO));

        mockMvc.perform(get(uriTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.rocketDtoList[0]._id").value(TEST_ROCKET_DTO.get_id()))
                .andExpect(jsonPath("$._embedded.rocketDtoList[0].rocket_id").value(TEST_ROCKET_DTO.getRocketId()))
                .andExpect(jsonPath("$._embedded.rocketDtoList[0].rocket_name").value(TEST_ROCKET_DTO.getRocketName()))
                .andExpect(jsonPath("$._embedded.rocketDtoList[0].rocket_type").value(TEST_ROCKET_DTO.getRocketType()));

        verify(rocketService).getAllRockets(any(Pageable.class), any(), any(), any(), any(), any());
    }

    @Test
    void itShouldReturnRocket_WhenGetRocketByRocketId() throws Exception {
        final String rocketId = TEST_ROCKET_DTO.getRocketId();

        when(rocketService.getRocketByRocketId(rocketId)).thenReturn(TEST_ROCKET_DTO);
        when(rocketResourceAssembler.toModel(TEST_ROCKET_DTO)).thenReturn(EntityModel.of(TEST_ROCKET_DTO));

        mockMvc.perform(get("/rockets/{rocketId}", rocketId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("rocket_id").value(rocketId))
                .andExpect(jsonPath("rocket_name").value(TEST_ROCKET_DTO.getRocketName()))
                .andExpect(jsonPath("rocket_type").value(TEST_ROCKET_DTO.getRocketType()));

        verify(rocketService).getRocketByRocketId(rocketId);
    }

    @Test
    void itShouldThrow_WhenTryingToGetRocketByInvalidRocketId() throws Exception {
        final String rocketId = "Invalid";
        String errorMessage = "Rocket with id = [%s] does not exist".formatted(rocketId);

        when(rocketService.getRocketByRocketId(rocketId)).thenThrow(new ResourceNotFoundException(errorMessage));

        mockMvc.perform(get(uriTemplate + "/{rocketId}", rocketId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value(errorMessage));

        verify(rocketService).getRocketByRocketId(rocketId);
    }

    @Test
    void itShouldCreateRocket() throws Exception {
        assertThatNoException().isThrownBy(() -> rocketService.createRocket(TEST_ROCKET_DTO_REQUEST));

        mockMvc.perform(post(uriTemplate)
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(TEST_ROCKET_DTO_REQUEST)))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(rocketService).createRocket(TEST_ROCKET_DTO_REQUEST);
    }

    @Test
    void itShouldThrow_WhenCreateRocketWithExistingId() throws Exception {
        String errorMessage = "Rocket with id = [%s] already exists".formatted(TEST_ROCKET_DTO_REQUEST.getId());

        doThrow(new DuplicateResourceException(errorMessage))
                .when(rocketService).createRocket(any(RocketDtoRequest.class));

        mockMvc.perform(post(uriTemplate)
                        .contentType(APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(TEST_ROCKET_DTO_REQUEST))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value(errorMessage));

        verify(rocketService, never()).createRocket(TEST_ROCKET_DTO_REQUEST);
    }

    @Test
    void itShouldThrow_WhenCreateRocketWithExistingRocketId() throws Exception {
        String errorMessage = "Rocket with rocket id = [%s] already exists".formatted(TEST_ROCKET_DTO_REQUEST.getRocketId());

        doThrow(new DuplicateResourceException(errorMessage))
                .when(rocketService).createRocket(any(RocketDtoRequest.class));

        mockMvc.perform(post(uriTemplate)
                        .contentType(APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(TEST_ROCKET_DTO_REQUEST))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value(errorMessage));

        verify(rocketService, never()).createRocket(TEST_ROCKET_DTO_REQUEST);
    }

    @Test
    void itShouldThrow_WhenCreateRocketWithExistingRocketName() throws Exception {
        String errorMessage = "Rocket with name = [%s] already exists".formatted(TEST_ROCKET_DTO_REQUEST.getRocketName());

        doThrow(new DuplicateResourceException(errorMessage))
                .when(rocketService).createRocket(any(RocketDtoRequest.class));

        mockMvc.perform(post(uriTemplate)
                        .contentType(APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(TEST_ROCKET_DTO_REQUEST))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value(errorMessage));

        verify(rocketService, never()).createRocket(TEST_ROCKET_DTO_REQUEST);
    }

    @Test
    void itShouldDeleteRocket() throws Exception {
        doNothing().when(rocketService).deleteRocketByRocketId(TEST_ROCKET_DTO_REQUEST.getRocketId());

        mockMvc.perform(delete(uriTemplate + "/{rocketId}", TEST_ROCKET_DTO_REQUEST.getRocketId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void itShouldThrow_WhenDeleteRocketWithUnknownRocketId() throws Exception {
        String errorMessage = "Rocket with id = [%s] not found".formatted(TEST_ROCKET_DTO_REQUEST.getRocketId());

        doThrow(new ResourceNotFoundException(errorMessage))
                .when(rocketService).deleteRocketByRocketId(TEST_ROCKET_DTO_REQUEST.getRocketId());

        mockMvc.perform(delete(uriTemplate + "/{rocketId}", TEST_ROCKET_DTO_REQUEST.getRocketId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value(errorMessage));
    }
}