package com.matiaszapillon.elevator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matiaszapillon.elevator.entity.ElevatorDTO;
import com.matiaszapillon.elevator.service.ElevatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicElevatorController.class)
class PublicElevatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    @Qualifier("publicElevatorService")
    private ElevatorService elevatorService;
    @Test
    void findAll() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/v1/publicelevators")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void givenCorrectRequest_whenUpdateElevator_thenShouldReturnNoContent() throws Exception {
        String body = "{\"weight\":\"150\", \"floor\":\"45\"}";
        ElevatorDTO elevatorDTO =  objectMapper.readValue(body, ElevatorDTO.class);
        Mockito.doNothing().when(elevatorService).updateElevator(1L, elevatorDTO);
        this.mockMvc
                .perform(put("/api/v1/publicelevators/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenMissingMandatoryFields_whenUpdateElevator_thenShouldReturnProperException() throws Exception {
        String body = "{\n" +
                "    \"keycode\":\"2222\"\n" +
                "}";
        ElevatorDTO elevatorDTO = objectMapper.readValue(body, ElevatorDTO.class);

        Mockito.doNothing().when(elevatorService).updateElevator(1L, elevatorDTO);
        MvcResult result = this.mockMvc
                .perform(put("/api/v1/publicelevators/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest()).andReturn();
        String response = result.getResponse().getContentAsString();

        String expectedResponse = "{\"weight\":\"weight is mandatory.\",\"floor\":\"floor is mandatory.\"}";
        Assertions.assertEquals(expectedResponse, response);
    }

}