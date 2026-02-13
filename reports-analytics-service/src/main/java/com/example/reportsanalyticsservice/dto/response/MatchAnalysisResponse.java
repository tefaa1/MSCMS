package com.example.reportsanalyticsservice.dto.response;

import java.time.LocalDateTime;

public record MatchAnalysisResponse(
        Long id,
        Long matchId,
        Long teamId,
        Integer possession,
        Integer shots,
        Integer shotsOnTarget,
        Integer passes,
        Integer passAccuracy,
        Integer tackles,
        Integer fouls,
        String heatmapFilePath,
        String heatmapType,
        String keyMoments,
        String tacticalAnalysis,
        String playerRatings,
        String analyzedByUserKeycloakId,
        LocalDateTime analyzedAt,
        String notes,
        String attachments
) {}
