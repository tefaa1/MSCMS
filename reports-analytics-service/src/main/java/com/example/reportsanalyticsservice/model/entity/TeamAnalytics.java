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
@Table(name = "team_analytics")
public class TeamAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teamId;

    private LocalDate periodStart;
    private LocalDate periodEnd;

    // Match statistics
    private Integer totalMatches;
    private Integer wins;
    private Integer draws;
    private Integer losses;

    private Integer goalsFor;
    private Integer goalsAgainst;

    // Training influence
    private Double averageTeamFitnessScore;      // avg from Medical/Fitness
    private Integer totalInjuries;               // from Medical service
    private Integer totalTrainingSessions;       // aggregated

    // KPIs (stored as JSON)
    private String kpiData;
    private String trends;

    private LocalDateTime calculatedAt;
    private String notes;
}
