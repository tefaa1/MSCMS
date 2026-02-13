package com.example.reportsanalyticsservice.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PlayerAnalyticsResponse(
        Long id,
        String playerKeycloakId,
        Long teamId,
        LocalDate periodStart,
        LocalDate periodEnd,
        Integer totalMatches,
        Integer totalGoals,
        Integer totalAssists,
        Double averageRating,
        Integer totalTrainingSessions,
        Integer attendanceRate,
        Integer currentInjuries,
        Double averageFitnessScore,
        Integer fitnessTestsCount,
        String kpiData,
        String trends,
        LocalDateTime calculatedAt,
        String notes
) {}
