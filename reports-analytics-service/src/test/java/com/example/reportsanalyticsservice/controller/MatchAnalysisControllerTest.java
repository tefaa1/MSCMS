package com.example.reportsanalyticsservice.controller;

import com.example.reportsanalyticsservice.dto.request.MatchAnalysisRequest;
import com.example.reportsanalyticsservice.dto.response.MatchAnalysisResponse;
import com.example.reportsanalyticsservice.service.MatchAnalysisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchAnalysisControllerTest {

    @Mock
    private MatchAnalysisService matchAnalysisService;

    @InjectMocks
    private MatchAnalysisController matchAnalysisController;

    @Test
    void testCreateMatchAnalysis() {
        // Given
        MatchAnalysisRequest request = new MatchAnalysisRequest(
                1L,
                "analyst-keycloak-id-1",
                55,
                12,
                8,
                450,
                85.5,
                25,
                10,
                5,
                2,
                2,
                0,
                "Key moments data",
                "Tactical analysis data",
                "Player ratings data",
                "Heatmap data",
                "POSSESSION"
        );

        MatchAnalysisResponse response = new MatchAnalysisResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                55,
                12,
                8,
                450,
                85.5,
                25,
                10,
                5,
                2,
                2,
                0,
                "Key moments data",
                "Tactical analysis data",
                "Player ratings data",
                "Heatmap data",
                "POSSESSION",
                LocalDateTime.now()
        );

        given(matchAnalysisService.create(request)).willReturn(response);

        // When
        ResponseEntity<MatchAnalysisResponse> result = matchAnalysisController.create(request);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        assertThat(result.getBody().matchId()).isEqualTo(1L);
        assertThat(result.getBody().analyzedByUserKeycloakId()).isEqualTo("analyst-keycloak-id-1");
        verify(matchAnalysisService, times(1)).create(request);
    }

    @Test
    void testUpdateMatchAnalysis() {
        // Given
        Long id = 1L;
        MatchAnalysisRequest request = new MatchAnalysisRequest(
                1L,
                "analyst-keycloak-id-1",
                60,
                15,
                10,
                500,
                88.0,
                30,
                8,
                6,
                1,
                3,
                1,
                "Updated key moments",
                "Updated tactical analysis",
                "Updated player ratings",
                "Updated heatmap data",
                "SHOTS"
        );

        MatchAnalysisResponse response = new MatchAnalysisResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                60,
                15,
                10,
                500,
                88.0,
                30,
                8,
                6,
                1,
                3,
                1,
                "Updated key moments",
                "Updated tactical analysis",
                "Updated player ratings",
                "Updated heatmap data",
                "SHOTS",
                LocalDateTime.now()
        );

        given(matchAnalysisService.update(id, request)).willReturn(response);

        // When
        ResponseEntity<MatchAnalysisResponse> result = matchAnalysisController.update(id, request);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        assertThat(result.getBody().possession()).isEqualTo(60);
        verify(matchAnalysisService, times(1)).update(id, request);
    }

    @Test
    void testGetMatchAnalysisById() {
        // Given
        Long id = 1L;
        MatchAnalysisResponse response = new MatchAnalysisResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                55,
                12,
                8,
                450,
                85.5,
                25,
                10,
                5,
                2,
                2,
                0,
                "Key moments data",
                "Tactical analysis data",
                "Player ratings data",
                "Heatmap data",
                "POSSESSION",
                LocalDateTime.now()
        );

        given(matchAnalysisService.getById(id)).willReturn(response);

        // When
        ResponseEntity<MatchAnalysisResponse> result = matchAnalysisController.getById(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        verify(matchAnalysisService, times(1)).getById(id);
    }

    @Test
    void testGetAllMatchAnalyses() {
        // Given
        MatchAnalysisResponse response1 = new MatchAnalysisResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                55,
                12,
                8,
                450,
                85.5,
                25,
                10,
                5,
                2,
                2,
                0,
                "Key moments data",
                "Tactical analysis data",
                "Player ratings data",
                "Heatmap data",
                "POSSESSION",
                LocalDateTime.now()
        );

        MatchAnalysisResponse response2 = new MatchAnalysisResponse(
                2L,
                2L,
                "analyst-keycloak-id-2",
                45,
                10,
                5,
                380,
                78.0,
                20,
                15,
                4,
                3,
                1,
                1,
                "Key moments data 2",
                "Tactical analysis data 2",
                "Player ratings data 2",
                "Heatmap data 2",
                "PASSES",
                LocalDateTime.now()
        );

        List<MatchAnalysisResponse> responses = Arrays.asList(response1, response2);
        given(matchAnalysisService.getAll()).willReturn(responses);

        // When
        ResponseEntity<List<MatchAnalysisResponse>> result = matchAnalysisController.getAll();

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).hasSize(2);
        verify(matchAnalysisService, times(1)).getAll();
    }

    @Test
    void testDeleteMatchAnalysis() {
        // Given
        Long id = 1L;
        doNothing().when(matchAnalysisService).delete(id);

        // When
        ResponseEntity<Void> result = matchAnalysisController.delete(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(204);
        verify(matchAnalysisService, times(1)).delete(id);
    }
}
