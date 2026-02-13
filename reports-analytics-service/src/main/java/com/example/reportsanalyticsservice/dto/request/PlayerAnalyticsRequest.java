package com.example.reportsanalyticsservice.dto.request;

import com.example.reportsanalyticsservice.dto.validation.Create;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PlayerAnalyticsRequest(
        @NotBlank(groups = Create.class) String playerKeycloakId,
        @NotNull(groups = Create.class) @Positive(groups = Create.class) Long teamId,
        @NotNull(groups = Create.class) LocalDate periodStart,
        @NotNull(groups = Create.class) LocalDate periodEnd,
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
