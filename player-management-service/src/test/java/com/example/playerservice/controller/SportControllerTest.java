package com.example.playerservice.controller;

import com.example.playerservice.dto.request.SportRequest;
import com.example.playerservice.dto.response.SportResponse;
import com.example.playerservice.service.SportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SportControllerTest {

    @Mock
    private SportService sportService;

    @InjectMocks
    private SportController sportController;

    private SportRequest request;
    private SportResponse response;

    @BeforeEach
    void setUp() {
        request = new SportRequest("Football", 1L);
        response = new SportResponse(1L, "Football", 1L);
    }

    @Test
    void createSport_shouldReturnCreatedResponse() {
        given(sportService.createSport(any(SportRequest.class))).willReturn(response);

        ResponseEntity<SportResponse> result = sportController.createSport(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(sportService).createSport(eq(request));
    }

    @Test
    void updateSport_shouldReturnOkResponse() {
        Long id = 1L;
        given(sportService.updateSport(eq(id), any(SportRequest.class))).willReturn(response);

        ResponseEntity<SportResponse> result = sportController.updateSport(id, request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(sportService).updateSport(id, request);
    }

    @Test
    void getSport_shouldReturnOkResponse() {
        Long id = 1L;
        given(sportService.getSportById(id)).willReturn(response);

        ResponseEntity<SportResponse> result = sportController.getSport(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(sportService).getSportById(id);
    }

    @Test
    void getAllSports_shouldReturnList() {
        given(sportService.getAllSports()).willReturn(List.of(response));

        ResponseEntity<List<SportResponse>> result = sportController.getAllSports();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).containsExactly(response);
        verify(sportService).getAllSports();
    }

    @Test
    void deleteSport_shouldReturnNoContent() {
        Long id = 1L;

        ResponseEntity<Void> result = sportController.deleteSport(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(sportService).deleteSport(id);
    }
}

