package com.example.reportsanalyticsservice.model.entity;

import com.example.reportsanalyticsservice.model.enums.ExportFormat;
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
@Table(name = "report_exports")
public class ReportExport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @Enumerated(EnumType.STRING)
    private ExportFormat format;

    private String fileStoragePath;  // Path to exported file (stored externally)
    private Long fileSizeBytes;  // File size

    private Long exportedByUserId;  // Reference to User
    private LocalDateTime exportedAt;

    private LocalDateTime expiresAt;  // When the export file should be deleted
    private Integer downloadCount;  // How many times it was downloaded

    private String metadata;  // Additional export metadata
}

