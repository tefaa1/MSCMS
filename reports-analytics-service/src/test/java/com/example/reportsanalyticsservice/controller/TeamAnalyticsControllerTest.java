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

import java.time.LocalDate;
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
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2026, 5, 31),
                38,
                25,
                8,
                5,
                75,
                30,
                85.0,
                15,
                120,
                "KPI data",
                "Strong attack and midfield",
                LocalDateTime.of(2025, 6, 1, 10, 0),
                "Good team performance"
        );

        TeamAnalyticsResponse response = new TeamAnalyticsResponse(
                1L,
                1L,
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2026, 5, 31),
                38,
                25,
                8,
                5,
                75,
                30,
                85.0,
                15,
                120,
                "KPI data",
                "Strong attack and midfield",
                LocalDateTime.of(2025, 6, 1, 10, 0),
                "Good team performance"
        );

        given(teamAnalyticsService.create(request)).willReturn(response);

        // When
        ResponseEntity<TeamAnalyticsResponse> result = teamAnalyticsController.create(request);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        assertThat(result.getBody().teamId()).isEqualTo(1L);
        assertThat(result.getBody().totalMatches()).isEqualTo(38);
        verify(teamAnalyticsService, times(1)).create(request);
    }

    @Test
    void testUpdateTeamAnalytics() {
        // Given
        Long id = 1L;
        TeamAnalyticsRequest request = new TeamAnalyticsRequest(
                1L,
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2026, 5, 31),
                40,
                28,
                7,
                5,
                82,
                28,
                87.5,
                18,
                130,
                "Updated KPI data",
                "Excellent attack and improved defense",
                LocalDateTime.of(2025, 6, 2, 10, 0),
                "Strong team performance"
        );

        TeamAnalyticsResponse response = new TeamAnalyticsResponse(
                1L,
                1L,
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2026, 5, 31),
                40,
                28,
                7,
                5,
                82,
                28,
                87.5,
                18,
                130,
                "Updated KPI data",
                "Excellent attack and improved defense",
                LocalDateTime.of(2025, 6, 2, 10, 0),
                "Strong team performance"
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
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2026, 5, 31),
                38,
                25,
                8,
                5,
                75,
                30,
                85.0,
                15,
                120,
                "KPI data",
                "Strong attack and midfield",
                LocalDateTime.of(2025, 6, 1, 10, 0),
                "Good team performance"
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
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2026, 5, 31),
                38,
                25,
                8,
                5,
                75,
                30,
                85.0,
                15,
                120,
                "KPI data",
                "Strong attack and midfield",
                LocalDateTime.of(2025, 6, 1, 10, 0),
                "Good team performance"
        );

        TeamAnalyticsResponse response2 = new TeamAnalyticsResponse(
                2L,
                2L,
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2026, 5, 31),
                38,
                20,
                10,
                8,
                60,
                35,
                82.0,
                12,
                110,
                "KPI data 2",
                "Solid defense",
                LocalDateTime.of(2025, 6, 1, 10, 0),
                "Stable performance"
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
