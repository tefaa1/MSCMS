package com.example.reportsanalyticsservice.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TeamAnalyticsResponse(
        Long id,
        Long teamId,
        LocalDate periodStart,
        LocalDate periodEnd,
        Integer totalMatches,
        Integer wins,
        Integer draws,
        Integer losses,
        Integer goalsFor,
        Integer goalsAgainst,
        Double averageTeamFitnessScore,
        Integer totalInjuries,
        Integer totalTrainingSessions,
        String kpiData,
        String trends,
        LocalDateTime calculatedAt,
        String notes
) {}
