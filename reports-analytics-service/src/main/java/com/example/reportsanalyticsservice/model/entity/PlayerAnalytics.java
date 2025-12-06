package com.example.reportsanalyticsservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "player_analytics")
public class PlayerAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long playerId;
    private Long teamId;

    private LocalDate periodStart;
    private LocalDate periodEnd;

    // Performance Metrics (aggregated from other services)
    private Integer totalMatches;
    private Integer totalGoals;
    private Integer totalAssists;
    private Double averageRating;
    private Integer totalTrainingSessions;
    private Integer attendanceRate;  // Percentage

    // Fitness Metrics (from Medical & Fitness Service)
    private Integer currentInjuries;
    private Double averageFitnessScore;
    private Integer fitnessTestsCount;

    // KPI Metrics
    private String kpiData;  // JSON string for various KPIs
    private String trends;  // JSON string for performance trends

    private LocalDateTime calculatedAt;
    private String notes;
}

