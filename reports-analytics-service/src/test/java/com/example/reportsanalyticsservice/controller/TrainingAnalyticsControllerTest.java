package com.example.reportsanalyticsservice.controller;

import com.example.reportsanalyticsservice.dto.request.TrainingAnalyticsRequest;
import com.example.reportsanalyticsservice.dto.response.TrainingAnalyticsResponse;
import com.example.reportsanalyticsservice.service.TrainingAnalyticsService;
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
class TrainingAnalyticsControllerTest {

    @Mock
    private TrainingAnalyticsService trainingAnalyticsService;

    @InjectMocks
    private TrainingAnalyticsController trainingAnalyticsController;

    @Test
    void testCreateTrainingAnalytics() {
        // Given
        TrainingAnalyticsRequest request = new TrainingAnalyticsRequest(
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                150,
                92.5,
                90.0,
                "Technical: 60, Tactical: 50, Physical: 40",
                "High: 30%, Medium: 50%, Low: 20%",
                "Overall improvement observed",
                "Low correlation with injuries",
                "Increase high-intensity sessions"
        );

        TrainingAnalyticsResponse response = new TrainingAnalyticsResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                150,
                92.5,
                90.0,
                "Technical: 60, Tactical: 50, Physical: 40",
                "High: 30%, Medium: 50%, Low: 20%",
                "Overall improvement observed",
                "Low correlation with injuries",
                "Increase high-intensity sessions",
                LocalDateTime.now()
        );

        given(trainingAnalyticsService.create(request)).willReturn(response);

        // When
        ResponseEntity<TrainingAnalyticsResponse> result = trainingAnalyticsController.create(request);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        assertThat(result.getBody().teamId()).isEqualTo(1L);
        assertThat(result.getBody().season()).isEqualTo("2025/2026");
        verify(trainingAnalyticsService, times(1)).create(request);
    }

    @Test
    void testUpdateTrainingAnalytics() {
        // Given
        Long id = 1L;
        TrainingAnalyticsRequest request = new TrainingAnalyticsRequest(
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                160,
                94.0,
                92.5,
                "Technical: 65, Tactical: 55, Physical: 40",
                "High: 35%, Medium: 50%, Low: 15%",
                "Significant improvement in technical skills",
                "No significant correlation",
                "Maintain current training intensity"
        );

        TrainingAnalyticsResponse response = new TrainingAnalyticsResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                160,
                94.0,
                92.5,
                "Technical: 65, Tactical: 55, Physical: 40",
                "High: 35%, Medium: 50%, Low: 15%",
                "Significant improvement in technical skills",
                "No significant correlation",
                "Maintain current training intensity",
                LocalDateTime.now()
        );

        given(trainingAnalyticsService.update(id, request)).willReturn(response);

        // When
        ResponseEntity<TrainingAnalyticsResponse> result = trainingAnalyticsController.update(id, request);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        assertThat(result.getBody().totalSessions()).isEqualTo(160);
        verify(trainingAnalyticsService, times(1)).update(id, request);
    }

    @Test
    void testGetTrainingAnalyticsById() {
        // Given
        Long id = 1L;
        TrainingAnalyticsResponse response = new TrainingAnalyticsResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                150,
                92.5,
                90.0,
                "Technical: 60, Tactical: 50, Physical: 40",
                "High: 30%, Medium: 50%, Low: 20%",
                "Overall improvement observed",
                "Low correlation with injuries",
                "Increase high-intensity sessions",
                LocalDateTime.now()
        );

        given(trainingAnalyticsService.getById(id)).willReturn(response);

        // When
        ResponseEntity<TrainingAnalyticsResponse> result = trainingAnalyticsController.getById(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(1L);
        verify(trainingAnalyticsService, times(1)).getById(id);
    }

    @Test
    void testGetAllTrainingAnalytics() {
        // Given
        TrainingAnalyticsResponse response1 = new TrainingAnalyticsResponse(
                1L,
                1L,
                "analyst-keycloak-id-1",
                "2025/2026",
                150,
                92.5,
                90.0,
                "Technical: 60, Tactical: 50, Physical: 40",
                "High: 30%, Medium: 50%, Low: 20%",
                "Overall improvement observed",
                "Low correlation with injuries",
                "Increase high-intensity sessions",
                LocalDateTime.now()
        );

        TrainingAnalyticsResponse response2 = new TrainingAnalyticsResponse(
                2L,
                2L,
                "analyst-keycloak-id-2",
                "2025/2026",
                140,
                88.0,
                85.0,
                "Technical: 55, Tactical: 45, Physical: 40",
                "High: 25%, Medium: 55%, Low: 20%",
                "Moderate improvement",
                "Moderate correlation with injuries",
                "Focus on injury prevention",
                LocalDateTime.now()
        );

        List<TrainingAnalyticsResponse> responses = Arrays.asList(response1, response2);
        given(trainingAnalyticsService.getAll()).willReturn(responses);

        // When
        ResponseEntity<List<TrainingAnalyticsResponse>> result = trainingAnalyticsController.getAll();

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).hasSize(2);
        verify(trainingAnalyticsService, times(1)).getAll();
    }

    @Test
    void testDeleteTrainingAnalytics() {
        // Given
        Long id = 1L;
        doNothing().when(trainingAnalyticsService).delete(id);

        // When
        ResponseEntity<Void> result = trainingAnalyticsController.delete(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(204);
        verify(trainingAnalyticsService, times(1)).delete(id);
    }
}
