package com.example.reportsanalyticsservice.controller;

import com.example.reportsanalyticsservice.dto.request.PlayerAnalyticsRequest;
import com.example.reportsanalyticsservice.dto.response.PlayerAnalyticsResponse;
import com.example.reportsanalyticsservice.service.PlayerAnalyticsService;
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
class PlayerAnalyticsControllerTest {

    @Mock
    private PlayerAnalyticsService playerAnalyticsService;

    @InjectMocks
    private PlayerAnalyticsController playerAnalyticsController;

    @Test
    void testCreatePlayerAnalytics() {
        // Given
        PlayerAnalyticsRequest request = new PlayerAnalyticsRequest(
                "player-keycloak-id-1",
                "analyst-keycloak-id-1",
                "2025/2026",
                25,
                10,
                5,
                7.5,
                80,
                95.0,
                2,
                85.5,
                "Improving",
                "KPI data"
        );

        PlayerAnalyticsResponse response = new PlayerAnalyticsResponse(
                1L,
                "player-keycloak-id-1",
                "analyst-keycloak-id-1",
                "2025/2026",
                25,
                10,
                5,
                7.5,
                80,
                95.0,
                2,
                85.5,
                "Improving",
                "KPI data",
                LocalDateTime.now()
        );

        given(playerAnalyticsService.create(request)).willReturn(response);

        // When
        ResponseEntity<PlayerAnalyticsResponse> result = playerAnalyticsController.create(request);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        assertThat(result.getBody().playerKeycloakId()).isEqualTo("player-keycloak-id-1");
        assertThat(result.getBody().season()).isEqualTo("2025/2026");
        verify(playerAnalyticsService, times(1)).create(request);
    }

    @Test
    void testUpdatePlayerAnalytics() {
        // Given
        Long id = 1L;
        PlayerAnalyticsRequest request = new PlayerAnalyticsRequest(
                "player-keycloak-id-1",
                "analyst-keycloak-id-1",
                "2025/2026",
                30,
                12,
                8,
                8.0,
                90,
                97.5,
                1,
                88.0,
                "Excellent",
                "Updated KPI data"
        );

        PlayerAnalyticsResponse response = new PlayerAnalyticsResponse(
                1L,
                "player-keycloak-id-1",
                "analyst-keycloak-id-1",
                "2025/2026",
                30,
                12,
                8,
                8.0,
                90,
                97.5,
                1,
                88.0,
                "Excellent",
                "Updated KPI data",
                LocalDateTime.now()
        );

        given(playerAnalyticsService.update(id, request)).willReturn(response);

        // When
        ResponseEntity<PlayerAnalyticsResponse> result = playerAnalyticsController.update(id, request);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        assertThat(result.getBody().matchesPlayed()).isEqualTo(30);
        verify(playerAnalyticsService, times(1)).update(id, request);
    }

    @Test
    void testGetPlayerAnalyticsById() {
        // Given
        Long id = 1L;
        PlayerAnalyticsResponse response = new PlayerAnalyticsResponse(
                1L,
                "player-keycloak-id-1",
                "analyst-keycloak-id-1",
                "2025/2026",
                25,
                10,
                5,
                7.5,
                80,
                95.0,
                2,
                85.5,
                "Improving",
                "KPI data",
                LocalDateTime.now()
        );

        given(playerAnalyticsService.getById(id)).willReturn(response);

        // When
        ResponseEntity<PlayerAnalyticsResponse> result = playerAnalyticsController.getById(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        verify(playerAnalyticsService, times(1)).getById(id);
    }

    @Test
    void testGetAllPlayerAnalytics() {
        // Given
        PlayerAnalyticsResponse response1 = new PlayerAnalyticsResponse(
                1L,
                "player-keycloak-id-1",
                "analyst-keycloak-id-1",
                "2025/2026",
                25,
                10,
                5,
                7.5,
                80,
                95.0,
                2,
                85.5,
                "Improving",
                "KPI data",
                LocalDateTime.now()
        );

        PlayerAnalyticsResponse response2 = new PlayerAnalyticsResponse(
                2L,
                "player-keycloak-id-2",
                "analyst-keycloak-id-2",
                "2025/2026",
                28,
                15,
                7,
                8.2,
                85,
                98.0,
                1,
                90.0,
                "Excellent",
                "KPI data 2",
                LocalDateTime.now()
        );

        List<PlayerAnalyticsResponse> responses = Arrays.asList(response1, response2);
        given(playerAnalyticsService.getAll()).willReturn(responses);

        // When
        ResponseEntity<List<PlayerAnalyticsResponse>> result = playerAnalyticsController.getAll();

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).hasSize(2);
        verify(playerAnalyticsService, times(1)).getAll();
    }

    @Test
    void testDeletePlayerAnalytics() {
        // Given
        Long id = 1L;
        doNothing().when(playerAnalyticsService).delete(id);

        // When
        ResponseEntity<Void> result = playerAnalyticsController.delete(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(204);
        verify(playerAnalyticsService, times(1)).delete(id);
    }
}
