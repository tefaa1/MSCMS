package com.example.reportsanalyticsservice.controller;

import com.example.reportsanalyticsservice.dto.request.TeamAnalyticsRequest;
import com.example.reportsanalyticsservice.dto.response.TeamAnalyticsResponse;
import com.example.reportsanalyticsservice.service.TeamAnalyticsService;
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
class TeamAnalyticsControllerTest {

    @Mock
    private TeamAnalyticsService teamAnalyticsService;

    @InjectMocks
    private TeamAnalyticsController teamAnalyticsController;

    @Test
    void testCreateTeamAnalytics() {
        // Given
        TeamAnalyticsRequest request = new TeamAnalyticsRequest(
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                38,
                25,
                8,
                5,
                75,
                30,
                45,
                15,
                58.5,
                85.0,
                "Strong attack and midfield",
                "Defensive vulnerabilities",
                "Improving steadily"
        );

        TeamAnalyticsResponse response = new TeamAnalyticsResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                38,
                25,
                8,
                5,
                75,
                30,
                45,
                15,
                58.5,
                85.0,
                "Strong attack and midfield",
                "Defensive vulnerabilities",
                "Improving steadily",
                LocalDateTime.now()
        );

        given(teamAnalyticsService.create(request)).willReturn(response);

        // When
        ResponseEntity<TeamAnalyticsResponse> result = teamAnalyticsController.create(request);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        assertThat(result.getBody().teamId()).isEqualTo(1L);
        assertThat(result.getBody().season()).isEqualTo("2025/2026");
        verify(teamAnalyticsService, times(1)).create(request);
    }

    @Test
    void testUpdateTeamAnalytics() {
        // Given
        Long id = 1L;
        TeamAnalyticsRequest request = new TeamAnalyticsRequest(
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                40,
                28,
                7,
                5,
                82,
                28,
                54,
                18,
                60.0,
                87.5,
                "Excellent attack and improved defense",
                "Occasional lapses in concentration",
                "Strong upward trend"
        );

        TeamAnalyticsResponse response = new TeamAnalyticsResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                40,
                28,
                7,
                5,
                82,
                28,
                54,
                18,
                60.0,
                87.5,
                "Excellent attack and improved defense",
                "Occasional lapses in concentration",
                "Strong upward trend",
                LocalDateTime.now()
        );

        given(teamAnalyticsService.update(id, request)).willReturn(response);

        // When
        ResponseEntity<TeamAnalyticsResponse> result = teamAnalyticsController.update(id, request);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        assertThat(result.getBody().wins()).isEqualTo(28);
        verify(teamAnalyticsService, times(1)).update(id, request);
    }

    @Test
    void testGetTeamAnalyticsById() {
        // Given
        Long id = 1L;
        TeamAnalyticsResponse response = new TeamAnalyticsResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                38,
                25,
                8,
                5,
                75,
                30,
                45,
                15,
                58.5,
                85.0,
                "Strong attack and midfield",
                "Defensive vulnerabilities",
                "Improving steadily",
                LocalDateTime.now()
        );

        given(teamAnalyticsService.getById(id)).willReturn(response);

        // When
        ResponseEntity<TeamAnalyticsResponse> result = teamAnalyticsController.getById(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        verify(teamAnalyticsService, times(1)).getById(id);
    }

    @Test
    void testGetAllTeamAnalytics() {
        // Given
        TeamAnalyticsResponse response1 = new TeamAnalyticsResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                38,
                25,
                8,
                5,
                75,
                30,
                45,
                15,
                58.5,
                85.0,
                "Strong attack and midfield",
                "Defensive vulnerabilities",
                "Improving steadily",
                LocalDateTime.now()
        );

        TeamAnalyticsResponse response2 = new TeamAnalyticsResponse(
                2L,
                2L,
                "analyst-keycloak-id-2",
                "2025/2026",
                38,
                20,
                10,
                8,
                60,
                35,
                25,
                12,
                52.0,
                82.0,
                "Solid defense",
                "Lacks attacking power",
                "Stable performance",
                LocalDateTime.now()
        );

        List<TeamAnalyticsResponse> responses = Arrays.asList(response1, response2);
        given(teamAnalyticsService.getAll()).willReturn(responses);

        // When
        ResponseEntity<List<TeamAnalyticsResponse>> result = teamAnalyticsController.getAll();

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).hasSize(2);
        verify(teamAnalyticsService, times(1)).getAll();
    }

    @Test
    void testDeleteTeamAnalytics() {
        // Given
        Long id = 1L;
        doNothing().when(teamAnalyticsService).delete(id);

        // When
        ResponseEntity<Void> result = teamAnalyticsController.delete(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(204);
        verify(teamAnalyticsService, times(1)).delete(id);
    }
}
