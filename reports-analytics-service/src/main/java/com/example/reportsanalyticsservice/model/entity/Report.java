package com.example.reportsanalyticsservice.model.entity;

import com.example.reportsanalyticsservice.model.enums.ReportStatus;
import com.example.reportsanalyticsservice.model.enums.ReportType;
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
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reportName;
    private String description;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    private Long createdByUserId;  // Reference to User (Sport Manager, Team Manager, etc.)
    private Long playerId;  // If player-specific report
    private Long teamId;  // If team-specific report
    private Long matchId;  // If match-specific report

    private String parameters;  // JSON string for report filters/parameters
    private String dataSource;  // Which services the data comes from

    private LocalDateTime createdAt;
    private LocalDateTime generatedAt;
    private LocalDateTime expiresAt;  // For temporary reports

    private String fileStoragePath;  // Path to generated report file (stored externally)
    private String metadata;  // Additional metadata about the report
}

