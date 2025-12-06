package com.example.reportsanalyticsservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match_analyses")
public class MatchAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long matchId;  // Reference to Match
    private Long teamId;  // Which team's analysis

    // Match Statistics (metadata only - actual data from Training & Match Service)
    private Integer possession;
    private Integer shots;
    private Integer shotsOnTarget;
    private Integer passes;
    private Integer passAccuracy;  // Percentage
    private Integer tackles;
    private Integer fouls;

    // Heatmap Metadata (file references stored externally)
    private String heatmapFilePath;  // Path to heatmap image file
    private String heatmapType;  // e.g., "Player Movement", "Ball Position", "Pressure Map"

    // Analysis Data
    private String keyMoments;  // JSON string for key match moments
    private String tacticalAnalysis;  // Tactical insights
    private String playerRatings;  // JSON string for player ratings

    private Long analyzedByUserId;  // Reference to Performance Analyst or Coach
    private LocalDateTime analyzedAt;

    private String notes;
    private String attachments;  // Additional file references
}

